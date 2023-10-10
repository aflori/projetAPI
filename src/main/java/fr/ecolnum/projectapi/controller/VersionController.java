package fr.ecolnum.projectapi.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(path = "/api/version")
public class VersionController {

    @Operation(summary = "return API version")
    @ApiResponse(
            description = "return the version of the API",
            responseCode = "200"
    )
    @GetMapping
    public ResponseEntity<String> getVersion()
    {
        return new ResponseEntity<>("{\"version\":\"1.5\"}", HttpStatusCode.valueOf(200));
    }

}
