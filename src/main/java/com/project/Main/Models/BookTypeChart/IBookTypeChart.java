package com.project.Main.Models.BookTypeChart;

import com.project.Main.Models.RevenueChart.Repositories.IChartRepository;
import com.project.Main.Models.CommonState.LoadState.LoadState;

import java.util.List;
import java.util.Map;

public interface IBookTypeChart {

    public List<Map.Entry<String, Integer>> getChartData();

    public void setChartData(List<Map.Entry<String, Integer>> chartData);

    public IChartRepository getChartRepository();

    public void setChartRepository(IChartRepository chartRepository);

    public LoadState getTypeOfBookIssuedData();
}
