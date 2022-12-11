package fivesenses.server.fivesenses.controller;

import fivesenses.server.fivesenses.dto.Meta;
import fivesenses.server.fivesenses.dto.Result;
import fivesenses.server.fivesenses.service.S3Service;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/files")
public class FileController {

    private final S3Service s3Service;

    @PutMapping("/upload")
    public ResponseEntity<Result<String>> upload(@RequestParam("data") MultipartFile multipartFile) throws IOException {
        String imgLocation = s3Service.uploadWithUUID(multipartFile);

        Result<String> result = new Result<>(new Meta(HttpStatus.OK.value()), imgLocation);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @DeleteMapping("/delete")
    public ResponseEntity<?> delete(@RequestParam String imageLocation) {
        String[] s = imageLocation.split("/");
        String fileName = s[s.length - 1];

        s3Service.deleteFile(fileName);

        Result<?> result = new Result<>(new Meta(HttpStatus.OK.value()));
        return new ResponseEntity<>(result, HttpStatus.OK);
    }
}
