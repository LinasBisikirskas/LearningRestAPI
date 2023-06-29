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

    @GetMapping("/building-management/managed-buildings")
    public ResponseEntity<List<Building>> getAllBuildings() {
        try {
            List<Building> buildingList = new ArrayList<>();
            buildingRepo.findAll().forEach(buildingList::add);
            if (buildingList.isEmpty()) {
                return new ResponseEntity<>(new ArrayList<>(), HttpStatus.OK);
            }
            return new ResponseEntity<>(buildingList, HttpStatus.OK);
        } catch (Exception ex) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/building-management/managed-buildings/{id}")
    public ResponseEntity<Building> getBuildingById(@PathVariable Long id) {
        Optional<Building> buildingData = buildingRepo.findById(id);
        if (buildingData.isPresent()) {
            return new ResponseEntity<>(buildingData.get(), HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping("/building-management/managed-buildings")
    public ResponseEntity<Building> addBuilding(@RequestBody Building building) {
        Building buildingObj = buildingRepo.save(building);
        return new ResponseEntity<>(buildingObj, HttpStatus.OK);
    }

    @PostMapping("/building-management/managed-buildings/{id}")
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

    @DeleteMapping("/building-management/managed-buildings/{id}")
    public ResponseEntity<Object> deleteBuildingById(@PathVariable Long id) {
        Optional<Building> building = buildingRepo.findById(id);
        if (building.isPresent()) {
            buildingRepo.deleteById(id);
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}