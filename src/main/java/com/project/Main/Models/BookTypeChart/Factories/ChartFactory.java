package com.project.Main.Models.BookTypeChart.Factories;

import com.project.Main.Models.BookTypeChart.BookTypeChart;
import com.project.Main.Models.BookTypeChart.IBookTypeChart;
import com.project.Main.Models.RevenueChart.IRevenueChart;
import com.project.Main.Models.RevenueChart.RevenueChart;
import com.project.Main.Models.RevenueChart.Repositories.IChartRepository;

import java.util.List;
import java.util.Map;

public class ChartFactory implements IChartFactory {

    private static IChartFactory chartFactory;

    private ChartFactory() {
    }

    public static IChartFactory instance() {
        if (chartFactory == null) {
            chartFactory = new ChartFactory();
        }
        return chartFactory;
    }

    @Override
    public IBookTypeChart createBookTypeChart(IChartRepository chartRepository) {
        return new BookTypeChart(chartRepository);
    }

    @Override
    public IBookTypeChart createBookTypeChart(List<Map.Entry<String, Integer>> chartData, IChartRepository chartRepository) {
        return new BookTypeChart(chartData, chartRepository);
    }

    @Override
    public IRevenueChart createRevenueChart(IChartRepository chartRepository) {
        return new RevenueChart(chartRepository);
    }

    @Override
    public IRevenueChart createRevenueChart(List<Map.Entry<String, Double>> chartData, IChartRepository chartRepository) {
        return new RevenueChart(chartData, chartRepository);
    }

}
