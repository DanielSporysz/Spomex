package pl.spomex.bazodan.license;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.spomex.bazodan.exception.BadRequest;
import pl.spomex.bazodan.truck.Truck;
import pl.spomex.bazodan.truck.TruckService;

import java.util.ArrayList;
import java.util.List;

@Service
public class LicenseService {

    @Autowired
    private LicenseRepository licenseRepository;

    @Autowired
    private TruckService truckService;

    public List<License> getAllLicenses() {
        List<License> licenses = new ArrayList<>();
        licenseRepository.findAll().forEach(licenses::add);
        return licenses;
    }

    public License getLicense(Integer id) {
        return licenseRepository.findById(id).orElse(null);
    }

    public License addLicense(License license) throws BadRequest {
        validateLicense(license);
        return licenseRepository.save(license);
    }

    public License updateLicense(License license, Integer id) throws BadRequest {
        validateLicense(license);
        if (license.getId() == null || !license.getId().equals(id)) {
            throw new BadRequest("ID in payload does not match the URL");
        }
        return licenseRepository.save(license);
    }

    public void deleteLicense(Integer id) {
        licenseRepository.deleteById(id);
    }

    public void validateLicense(License license) throws BadRequest {
        if (license == null) {
            throw new BadRequest("Cannot construct License with given information");
        }

        // Validate dates
        if (license.getIssueDate() == null) {
            throw new BadRequest("Missing \"issueDate\".");
        }
        if (license.getExpDate() == null) {
            throw new BadRequest("Missing \"expDate\".");
        }
        if (license.getExpDate().isBefore(license.getIssueDate())) {
            throw new BadRequest("Incorrect \"expDate\" and \"issueDate\".");
        }

        // Validate truck
        if (license.getTruck() == null) {
            throw new BadRequest("Missing \"truck\".");
        }
        if (license.getTruck().getId() == null) {
            throw new BadRequest("Missing \"truck\" id.");
        }
        Truck targetTruck = truckService.getTruck(license.getTruck().getId());
        if (targetTruck == null) {
            throw new BadRequest("There's no such \"truck\" (ID=" + license.getTruck().getId().toString() + ").");
        }
        if (targetTruck.getLicense() != null && targetTruck.getLicense().getId() != null) {
            deleteLicense(targetTruck.getLicense().getId());
        }
    }
}
