package com.LearningRestAPI.Controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.LearningRestAPI.model.Building;
import com.LearningRestAPI.repo.BuildingRepo;

@RestController
public class Controller {

    @Autowired
    private BuildingRepo buildingRepo;

    @GetMapping("/getAllBuildings")
    public ResponseEntity<List<Building>> getAllBuildings() {
        try {
            List<Building> buildingList = new ArrayList<>();
            buildingRepo.findAll().forEach(buildingList::add);

            if (buildingList.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(buildingList, HttpStatus.OK);
        } catch (Exception ex) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/getBuildingById/{id}")
    public ResponseEntity<Building> getBuildingByID(@PathVariable Long id) {
        Optional<Building> buildingData = buildingRepo.findById(id);

        if (buildingData.isPresent()) {
            return new ResponseEntity<>(buildingData.get(), HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping("/addBuilding")
    public ResponseEntity<Building> addBuilding(@RequestBody Building building) {
        Building buildingObj = buildingRepo.save(building);

        return new ResponseEntity<>(buildingObj, HttpStatus.OK);


    }

    @PostMapping("/updateBuildingById/{id}")
    public ResponseEntity<Building> updateBuildingById(@PathVariable Long id, @RequestBody Building newBuildingData) {
        Optional<Building> oldBuildingData = buildingRepo.findById(id);
        if (oldBuildingData.isPresent()) {
            Building updatedBuildingData = oldBuildingData.get();
            updatedBuildingData.setAddress(newBuildingData.getAddress());
            updatedBuildingData.setOwner(newBuildingData.getOwner());
            updatedBuildingData.setSquareMeters(newBuildingData.getSquareMeters());
            updatedBuildingData.setBuildingValue(newBuildingData.getBuildingValue());
            updatedBuildingData.setBuildingType(newBuildingData.getBuildingType());

            Building buildingObj = buildingRepo.save(updatedBuildingData);
            return new ResponseEntity<>(buildingObj, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("/deteleBuildingById/{id}")
    public ResponseEntity<Object> deleteBuildingById(@PathVariable Long id) {
        buildingRepo.deleteById(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
