package com.project.Main.MockRepositories;

import com.project.Main.Database.State.DatabaseState;
import com.project.Main.Database.State.SuccessState;
import com.project.Main.Models.BookTypeChart.Factories.ChartFactory;
import com.project.Main.Models.BookTypeChart.IBookTypeChart;
import com.project.Main.Models.RevenueChart.IRevenueChart;
import com.project.Main.Models.RevenueChart.Repositories.IChartRepository;

import java.util.*;

public class MockChartRepository implements IChartRepository {

    @Override
    public Map.Entry<IBookTypeChart, DatabaseState> getTypeOfBookIssuedData() {

        Random random = new Random();

        List<String> typesOfBook = new ArrayList<String>() {
            {
                add("Mystery");
                add("Thriller");
                add("Horror");
                add("Romance");
                add("Historical");
                add("Western");
            }
        };

        List<Map.Entry<String, Integer>> chartData = new ArrayList<Map.Entry<String, Integer>>();

        for (String type : typesOfBook) {
            chartData.add(new AbstractMap.SimpleEntry<>(type, random.nextInt(500)));
        }

        IBookTypeChart chart = ChartFactory.instance().createBookTypeChart(chartData, this);

        return new AbstractMap.SimpleEntry<>(chart, new SuccessState());
    }

    @Override
    public Map.Entry<IRevenueChart, DatabaseState> getRevenueData() {

        Random random = new Random();

        List<String> typesOfBook = new ArrayList<String>() {
            {
                add("January");
                add("February");
                add("March");
                add("April");
                add("May");
                add("June");
                add("July");
                add("August");
                add("September");
                add("October");
                add("November");
                add("December");
            }
        };

        List<Map.Entry<String, Double>> chartData = new ArrayList<Map.Entry<String, Double>>();

        for (String type : typesOfBook) {
            chartData.add(new AbstractMap.SimpleEntry<>(type, random.nextDouble()));
        }

        IRevenueChart chart = ChartFactory.instance().createRevenueChart(chartData, this);

        return new AbstractMap.SimpleEntry<>(chart, new SuccessState());
    }
}
