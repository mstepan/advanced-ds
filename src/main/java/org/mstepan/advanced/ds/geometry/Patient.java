package org.mstepan.advanced.ds.geometry;

import java.time.LocalDate;

public class Patient implements ComparableDimensions<Patient> {

    private final int age;
    private final double bloodPressure;
    private final boolean vaccinated;
    private final Gender gender;
    private final LocalDate birthDate;

    public Patient(int age, double bloodPressure, boolean vaccinated, Gender gender, LocalDate birthDate) {
        this.age = age;
        this.bloodPressure = bloodPressure;
        this.vaccinated = vaccinated;
        this.gender = gender;
        this.birthDate = birthDate;
    }

    @Override
    public int dimensionsCount() {
        return 5;
    }

    @Override
    public int compareWith(Patient other, int dimIndex) {
        if (dimIndex == 0) {
            return Integer.compare(age, other.age);
        }
        if (dimIndex == 1) {
            return Double.compare(bloodPressure, other.bloodPressure);
        }
        if (dimIndex == 2) {
            return Boolean.compare(vaccinated, other.vaccinated);
        }
        if (dimIndex == 3) {
            return gender.compareTo(other.gender);
        }
        if (dimIndex == 4) {
            return birthDate.compareTo(other.birthDate);
        }

        throw new IllegalStateException(String.format("Can't obtain dimension value for class %s with index %d, " +
                                                              "dimension should be in range [%d ... %d]",
                                                      Patient.class.getCanonicalName(),
                                                      dimIndex,
                                                      0, dimensionsCount() - 1));
    }

    public enum Gender {
        MALE,
        FEMALE,
        OTHER,
        UNKNOWN;
    }
}
