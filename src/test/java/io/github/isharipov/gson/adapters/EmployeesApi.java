package io.github.isharipov.gson.adapters;

import retrofit2.Call;
import retrofit2.http.GET;

import java.util.List;

public interface EmployeesApi {
    @GET("/employees")
    Call<List<Employee>> employees();
}
