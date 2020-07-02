package io.github.isharipov.gson.adapters;

@JsonType(
        property = "type",
        subtypes = {
                @JsonSubtype(clazz = CommissionEmployee.class, name = "commission"),
                @JsonSubtype(clazz = HourlyEmployee.class, name = "hourly"),
                @JsonSubtype(clazz = SalariedEmployee.class, name = "salaried")
        }
)
public abstract class Employee {
    private final String id;
    private final String type;
    private final String firstName;
    private final String lastName;
    private final String ssn;

    public Employee(String id, String type, String firstName, String lastName, String ssn) {
        this.id = id;
        this.type = type;
        this.firstName = firstName;
        this.lastName = lastName;
        this.ssn = ssn;
    }

    public String getId() {
        return id;
    }

    public String getType() {
        return type;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getSsn() {
        return ssn;
    }

    @Override
    public String toString() {
        return "Employee{" +
                "id='" + id + '\'' +
                ", type='" + type + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", socialSecurityNumber='" + ssn + '\'' +
                '}';
    }
}
