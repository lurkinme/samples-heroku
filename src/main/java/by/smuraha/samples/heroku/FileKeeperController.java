package by.smuraha.samples.heroku;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class FileKeeperController {

    private String file = "";

    @PostMapping(path = "/save", produces = MediaType.TEXT_PLAIN_VALUE)
    public ResponseEntity<String> notify(@RequestBody String body) {
        file = body;
        return new ResponseEntity<String>(HttpStatus.CREATED);
    }

    @GetMapping(path = "/.well-known/apple-app-site-association")
    public ResponseEntity<String> getFile() {
        return new ResponseEntity<String>(file, HttpStatus.OK);
    }
}
