package com.project.Main.Models.BookTypeChart.Factories;

import com.project.Main.Models.BookTypeChart.IBookTypeChart;
import com.project.Main.Models.RevenueChart.IRevenueChart;
import com.project.Main.Models.RevenueChart.Repositories.IChartRepository;

import java.util.List;
import java.util.Map;

public interface IChartFactory {

    public IBookTypeChart createBookTypeChart(IChartRepository chartRepository);

    public IBookTypeChart createBookTypeChart(List<Map.Entry<String, Integer>> chartData, IChartRepository chartRepository);

    IRevenueChart createRevenueChart(IChartRepository chartRepository);

    IRevenueChart createRevenueChart(List<Map.Entry<String, Double>> chartData, IChartRepository chartRepository);
}
