package com.main.services;

import com.main.models.entity.dataBobotKriteria;
import com.main.models.kriteria.deleteDataKriteria;
import com.main.models.kriteria.insertDataKriteria;
import com.main.models.kriteria.loadDataKriteria;
import com.main.models.kriteria.updateDataKriteria;

public class authDataKriteria {

    public static Boolean insertBobotKriteria(String kriteria, int weight, String type) {
        return insertDataKriteria.insertData(kriteria, weight, type);
    }

    public static Boolean updateBobotKriteria(int idWeight, String kriteria, int weight, String type) {
        return updateDataKriteria.updateData(idWeight, kriteria, weight, type);
    }

    public static Boolean deleteBobotKriteria(int idWeight) {
        return deleteDataKriteria.deleteData(idWeight);
    }

    public static double getWeightById(int idWeight) {
        dataBobotKriteria model = loadDataKriteria.getDataById(idWeight);
        if (model != null) {
            return model.getWeight();
        } else {
            return 0.0;
        }
    }

    public static int getTotalKriteria() {
        return loadDataKriteria.getTotalKriteria();
    }

    public String validateBobotKriteriaInput(String kriteria, String weight, String type) {
        if ((kriteria == null || kriteria.isEmpty()) &&
                (weight == null || weight.isEmpty()) &&
                (type == null || type.isEmpty())) {
            return "ALL_FIELDS_EMPTY";
        } else if (kriteria == null || kriteria.isEmpty()) {
            return "KRITERIA_EMPTY";
        } else if (weight == null || weight.isEmpty()) {
            return "WEIGHT_EMPTY";
        } else if (type == null || type.isEmpty()) {
            return "TYPE_EMPTY";
        } else {
            return "VALID";
        }
    }

}
