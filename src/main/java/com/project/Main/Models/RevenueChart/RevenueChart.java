package com.project.Main.Models.RevenueChart;

import com.project.Main.Models.RevenueChart.Repositories.IChartRepository;
import com.project.Main.Database.State.DatabaseState;
import com.project.Main.Models.CommonState.LoadState.LoadSQLFailureState;
import com.project.Main.Models.CommonState.LoadState.LoadState;
import com.project.Main.Models.CommonState.LoadState.LoadSuccessState;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.Map;

public class RevenueChart implements IRevenueChart {

    @Getter @Setter private List<Map.Entry<String, Double>> chartData;

    @Getter @Setter private IChartRepository chartRepository;

    public RevenueChart(IChartRepository chartRepository) {
        this.chartRepository = chartRepository;
    }

    public RevenueChart(List<Map.Entry<String, Double>> chartData, IChartRepository chartRepository) {
        this.chartData = chartData;
        this.chartRepository = chartRepository;
    }

    @Override
    public LoadState getMonthlyRevenueData() {

        Map.Entry<IRevenueChart, DatabaseState> chartDataResponse = chartRepository.getRevenueData();

        if (chartDataResponse.getValue().isSuccess()) {
            IRevenueChart chart = chartDataResponse.getKey();
            this.setChartData(chart.getChartData());

            return new LoadSuccessState();
        }

        return new LoadSQLFailureState();
    }
}
