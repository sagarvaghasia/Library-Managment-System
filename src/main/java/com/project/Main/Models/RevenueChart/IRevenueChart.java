package com.project.Main.Models.RevenueChart;

import com.project.Main.Models.RevenueChart.Repositories.IChartRepository;
import com.project.Main.Models.CommonState.LoadState.LoadState;

import java.util.List;
import java.util.Map;

public interface IRevenueChart {


    public List<Map.Entry<String, Double>> getChartData();

    public void setChartData(List<Map.Entry<String, Double>> chartData);

    public IChartRepository getChartRepository();

    public void setChartRepository(IChartRepository chartRepository);

    public LoadState getMonthlyRevenueData();
}
