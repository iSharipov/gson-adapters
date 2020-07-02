package io.github.isharipov.gson.adapters;

import com.github.tomakehurst.wiremock.junit.WireMockRule;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.apache.commons.io.IOUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;

import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.get;
import static com.github.tomakehurst.wiremock.client.WireMock.urlEqualTo;
import static java.nio.charset.StandardCharsets.UTF_8;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;

public class PolymorphDeserializerTest {

    private static final int port = 65535;
    private EmployeesApi employeesApi;

    @Rule
    public WireMockRule wireMockRule = new WireMockRule(port);

    @Before
    public void setUp() throws Exception {

        Gson gson = new GsonBuilder()
                .registerTypeAdapter(Employee.class, new PolymorphDeserializer<Employee>())
                .create();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://localhost:65535")
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
        employeesApi = retrofit.create(EmployeesApi.class);

        wireMockRule.stubFor(
                get(urlEqualTo("/employees"))
                        .willReturn(aResponse()
                                .withStatus(200)
                                .withHeader("Content-Type", "application/json")
                                .withBody(IOUtils.toString(this.getClass().getResourceAsStream("/employees.json"), UTF_8.name()))));
    }

    @After
    public void tearDown() {
        wireMockRule.stop();
    }

    @Test
    public void deserialize() throws IOException {
        List<Employee> employees = employeesApi.employees().execute().body();
        assertThat(employees, hasSize(3));
        assertThat(employees.stream().anyMatch(employee -> employee instanceof CommissionEmployee), is(true));
        assertThat(employees.stream().anyMatch(employee -> employee instanceof HourlyEmployee), is(true));
        assertThat(employees.stream().anyMatch(employee -> employee instanceof SalariedEmployee), is(true));

        CommissionEmployee commissionEmployee = concreteEmployee(employees, CommissionEmployee.class);
        assertThat(commissionEmployee.getGrossSales(), is(BigDecimal.valueOf(1000000)));
        assertThat(commissionEmployee.getCommissionRate(), is(BigDecimal.valueOf(25)));

        HourlyEmployee hourlyEmployee = concreteEmployee(employees, HourlyEmployee.class);
        assertThat(hourlyEmployee.getWage(), is(BigDecimal.valueOf(1000)));
        assertThat(hourlyEmployee.getHours(), is(40));

        SalariedEmployee salariedEmployee = concreteEmployee(employees, SalariedEmployee.class);
        assertThat(salariedEmployee.getWeeklySalary(), is(BigDecimal.valueOf(1000)));
    }

    @SuppressWarnings("unchecked")
    private <T extends Employee> T concreteEmployee(List<Employee> employees, Class<T> clazz) {
        return employees.stream()
                .filter(employee -> clazz.isAssignableFrom(employee.getClass()))
                .map(employee -> (T) employee)
                .findFirst()
                .orElseThrow(IllegalStateException::new);
    }
}