import {Injectable} from "@angular/core";
import {EChartsOption} from "echarts";
import {PieChartData} from '../models/charts/pie-chart-data';
import {TopicAnalytics} from '../models/analytics/topic-analytics';
import {SourceAnalytics} from '../models/analytics/source-analytics';
import {GeneralAnalytics} from '../models/analytics/general-analytics';


@Injectable({
  providedIn:'root'
})
export class EchartsAdapterService {
  public createOptionsForPieChart(name: string, legendNames: string[], data: PieChartData[]): EChartsOption {
    return  {
      title: {
        left: '50%',
        text: name,
        textAlign: 'center',
      },
      tooltip: {
        trigger: 'item',
        formatter: '{a} <br/>{b} : {c} ({d}%)',
      },
      legend: {
        align: 'auto',
        bottom: 10,
        data: legendNames
      },
      calculable: false,
      series: [
        {
          name: 'area',
          type: 'pie',
          radius: [30, 110],
          roseType: 'area',
          data: data
        },
      ],
    };
  }

  public createOptionsForKeywordsChart(topicAnalytics: TopicAnalytics | SourceAnalytics | GeneralAnalytics): EChartsOption {
    return {
      legend: {
        data: ['Популярність ключових слів'], // Legend for keywords
        align: 'auto',
        bottom: 10,
        selectorPosition: 'end'
      },
      tooltip: {
        trigger: 'axis',
        axisPointer: {
          type: 'shadow' // Helps highlight bars
        }
      },
      xAxis: {
        type: 'category',
        data: topicAnalytics.keywordCounts.map(k => k.keyword), // Keywords as X-axis labels
        silent: false,
        splitLine: {
          show: false,
        },
        name: 'Ключові слова',
        nameLocation: 'middle',
        nameGap: 30
      },
      yAxis: {
        type: 'value',
        name: 'Кількість',
        nameLocation: 'middle',
        nameGap: 40
      },
      series: [
        {
          name: 'Популярність ключових слів', // Series name
          type: 'bar',
          data: topicAnalytics.keywordCounts.map(k => k.count), // Counts as bar heights
          animationDelay: idx => idx * 10, // Delay for animation
          itemStyle: {
            color: '#5470C6' // Customize bar color
          },
          label: {
            show: true,
            position: 'top' // Display values above bars
          }
        }
      ],
      animationEasing: 'elasticOut',
      animationDelayUpdate: idx => idx * 5
    };
  }

  public createOptionsForDataSet(sourceData: any[][]): EChartsOption {
    return  {
      legend: {
        align: 'auto',
        bottom: 10,
        selectorPosition: 'end'
      },
      title: {
        left: '50%',
        textAlign: 'center'
      },
      tooltip: {},
      dataset: {
        // Provide a set of data.
        source: sourceData,
      },
      // Declare an x-axis (category axis).
      // The category map the first column in the dataset by default.
      xAxis: { type: 'category' },
      // Declare a y-axis (value axis).
      yAxis: {},
      // Declare several 'bar' series,
      // every series will auto-map to each column by default.
      series: [{ type: 'bar' }, {type: 'bar'}],
    };
  }
}
