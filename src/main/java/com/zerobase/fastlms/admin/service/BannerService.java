package com.zerobase.fastlms.admin.service;

import com.zerobase.fastlms.admin.dto.BannerInput;
import com.zerobase.fastlms.admin.entity.Banner;
import com.zerobase.fastlms.admin.repository.BannerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BannerService {

    private final BannerRepository bannerRepository;
//    private static final String UPLOAD_DIR = "uploads/";

    @Value("${upload.dir}")
    private String UPLOAD_DIR;

    public Banner saveBanner(BannerInput bannerInput) {

        String originalFileName = this.uploadImage(bannerInput);

        Banner banner = bannerInput.toBanner(originalFileName);

        return bannerRepository.save(banner);
    }

    public List<BannerInput> list() {

        List<Banner> bannerList = bannerRepository.findAll();


        List<BannerInput> bannerInfoList = bannerList.stream()
                .map(banner ->
                        BannerInput.builder()
                                .bannerId(banner.getId())
                                .bannerName(banner.getBannerName())
                                .openYn(banner.isOpenYn())
                                .order(banner.getBannerOrder())
                                .imageFileName(banner.getImageFileName())
                                .imageLink(banner.getImageLink())
                                .newWindow(banner.getNewWindow())
                                .registerDate(banner.getRegisterDate())
                                .build()
                ).collect(Collectors.toList());

        return bannerInfoList;
    }

    public String uploadImage(BannerInput bannerInput){
        MultipartFile imageFile = bannerInput.getImageFile();
        String originalFileName;
        if (imageFile != null){
            originalFileName = UPLOAD_DIR + imageFile.getOriginalFilename();

            Path path = Paths.get("src/main/resources/static/uploadImage/" + imageFile.getOriginalFilename());

            try{
                Files.write(path, imageFile.getBytes());
                System.out.println("이미지 저장 성공");
            }catch (IOException e){
                throw new RuntimeException(e);
            }
        }else{
            originalFileName = null;
        }
        return originalFileName;
    }

    public void delete(List<Long> deleteList) {
        List<Banner> banners = bannerRepository.findAllById(deleteList);
        bannerRepository.deleteAllById(deleteList);

        for(Banner banner : banners){
            deleteImage(banner.getImageFileName());
        }
        System.out.println("삭제 완료");
    }

    public void deleteImage(String imageFileName){
        Path path = Paths.get("src/main/resources/static" + imageFileName);
        if (Files.exists(path)){
            if (imageFileName == null){
                return;
            }

            try{
                Files.delete(path);
                System.out.println("이미지 삭제 성공");
            }catch (IOException e){
                throw new RuntimeException(e);
            }

        }
    }

    public BannerInput detail(long bannerId){
        Banner banner = bannerRepository.findById(bannerId)
                .orElseThrow(() -> new RuntimeException("존재하지 않는 배너입니다."));

        return BannerInput.builder()
                .bannerId(banner.getId())
                .bannerName(banner.getBannerName())
                .order(banner.getBannerOrder())
                .imageLink(banner.getImageLink())
                .imageFileName(banner.getImageFileName())
                .openYn(banner.isOpenYn())
                .newWindow(banner.getNewWindow())
                .registerDate(banner.getRegisterDate())
                .build();
    }

    public void update(BannerInput bannerInput) {
        Banner banner = bannerRepository.findById(bannerInput.getBannerId())
                .orElseThrow(() -> new RuntimeException("존재하지 않는 배너 입니다."));
        this.deleteImage(banner.getImageFileName());
        banner.setBannerName(bannerInput.getBannerName());
        banner.setBannerOrder(bannerInput.getOrder());
        banner.setNewWindow(bannerInput.getNewWindow());
        banner.setImageLink(bannerInput.getImageLink());
        banner.setImageFileName(this.uploadImage(bannerInput));
        banner.setOpenYn(bannerInput.isOpenYn());

        bannerRepository.save(banner);
    }

    public List<String> imageList(){
        List<Banner> banners = bannerRepository.findAllByOrderByBannerOrderAsc();
        List<String> imageFileNameList = new ArrayList<>();

        banners.stream()
                .forEach(banner -> {
                    if (banner.isOpenYn()){
                        if (banner.getImageFileName() != null){
                            imageFileNameList.add(banner.getImageFileName());
                        }else{
                            imageFileNameList.add(banner.getImageLink());
                        }

                    }
                });

        System.out.println(imageFileNameList);
        return imageFileNameList;
    }
}
