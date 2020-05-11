package pl.spomex.bazodan.license;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.spomex.bazodan.exception.BadRequest;

import java.util.List;

@RestController
public class LicenseController {

    @Autowired
    private LicenseService licenseService;

    @GetMapping(value = "/licenses", produces = "application/json")
    public ResponseEntity<Object> getAllLicenses() {
        List<License> licenses = licenseService.getAllLicenses();
        return new ResponseEntity<>(licenses, HttpStatus.OK);
    }

    @GetMapping(value = "/licenses/{id}", produces = "application/json")
    public ResponseEntity<Object> getLicense(@PathVariable Integer id) {
        License license = licenseService.getLicense(id);
        if (license != null) {
            return new ResponseEntity<>(license, HttpStatus.OK);
        } else {
            return new ResponseEntity<>("License not found", HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping(value = "/licenses", produces = "text/plain")
    public ResponseEntity<Object> addLicense(@RequestBody License license) {
        try {
            License savedLicense = licenseService.addLicense(license);
            return new ResponseEntity<>(
                    "License created (ID=" + savedLicense.getId() + ")",
                    HttpStatus.CREATED);
        } catch (BadRequest e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return new ResponseEntity<>("An error occurred", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping(value = "/licenses/{id}", produces = "text/plain")
    public ResponseEntity<Object> updateLicense(@RequestBody License license, @PathVariable Integer id) {
        try {
            License updatedLicense = licenseService.updateLicense(license, id);
            return new ResponseEntity<>(
                    "License updated (ID=" + updatedLicense.getId() + ")",
                    HttpStatus.OK);
        } catch (BadRequest e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return new ResponseEntity<>("An error occurred", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
