package com.test.api.controller;

import com.test.api.dto.WebResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Public Endpoint")
@RestController
@RequestMapping("api/v1/")
public class PublicController {

    @GetMapping(
            path = "/home",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public WebResponse<String> home() {
        return WebResponse.<String>builder()
                .message("This is Homepage")
                .build();
    }

}
