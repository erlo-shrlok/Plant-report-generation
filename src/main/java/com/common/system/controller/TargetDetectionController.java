package com.common.system.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.*;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import java.util.Base64;
import java.util.Map;

@RestController
public class TargetDetectionController {

    /**
     * 上传图片页面
     * @param modelAndView
     * @return
     */
    @RequestMapping(value = "upload",method = RequestMethod.GET)
    public ModelAndView upload(ModelAndView modelAndView){
        modelAndView.setViewName("/system/admin/report/upload");
        return modelAndView;
    }

    /**
     * 识别资产数量
     * @param image
     * @return
     */
//    @PostMapping("/detect_assets")
//        public ResponseEntity<String> detectAssets(@RequestParam("file") MultipartFile image) {
//            try {
//                // 将上传的图片文件转换为 BASE64 编码的字符串
//                String encodedImage = Base64.getEncoder().encodeToString(image.getBytes());
//
//                // 创建 RestTemplate 对象
//                RestTemplate restTemplate = new RestTemplate();
//
//                // 设置请求头
//                HttpHeaders headers = new HttpHeaders();
//                headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
//
//                // 设置请求体（包含 BASE64 编码的图片）
//                MultiValueMap<String, String> requestBody = new LinkedMultiValueMap<>();
//                requestBody.add("image", encodedImage);
//
//                // 创建 HttpEntity
//                HttpEntity<MultiValueMap<String, String>> requestEntity = new HttpEntity<>(requestBody, headers);
//
//                // 发送 POST 请求并获取返回结果
//                String apiUrl = "http://localhost:5000/detect_assets";
//                ResponseEntity<String> response = restTemplate.exchange(apiUrl, HttpMethod.POST, requestEntity, String.class);
//
//                // 返回结果
//                return ResponseEntity.ok(response.getBody());
//            } catch (Exception e) {
//                e.printStackTrace();
//                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error: " + e.getMessage());
//            }
//        }
    @PostMapping("/detect_assets")
    public ModelAndView detectAssets(@RequestParam("file") MultipartFile image) {
        ModelAndView modelAndView = new ModelAndView("/system/admin/report/data");
        try {
            // 将上传的图片文件转换为 BASE64 编码的字符串
            String encodedImage = Base64.getEncoder().encodeToString(image.getBytes());

            // 创建 RestTemplate 对象
            RestTemplate restTemplate = new RestTemplate();

            // 设置请求头
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

            // 设置请求体（包含 BASE64 编码的图片）
            MultiValueMap<String, String> requestBody = new LinkedMultiValueMap<>();
            requestBody.add("image", encodedImage);

            // 创建 HttpEntity
            HttpEntity<MultiValueMap<String, String>> requestEntity = new HttpEntity<>(requestBody, headers);

            // 发送 POST 请求并获取返回结果
            String apiUrl = "http://localhost:5000/detect_assets";
            ResponseEntity<String> response = restTemplate.exchange(apiUrl, HttpMethod.POST, requestEntity, String.class);

            // 将 JSON 字符串转换为 Map 对象
            ObjectMapper objectMapper = new ObjectMapper();
            Map<String, Integer> resultMap = objectMapper.readValue(response.getBody(), new TypeReference<Map<String, Integer>>() {});

            // 将数据添加到 ModelAndView 对象
            modelAndView.addObject("resultMap", resultMap);
        } catch (Exception e) {
            e.printStackTrace();
            modelAndView.setViewName("error");
            modelAndView.addObject("errorMessage", "Error: " + e.getMessage());
        }
        return modelAndView;
    }
}
