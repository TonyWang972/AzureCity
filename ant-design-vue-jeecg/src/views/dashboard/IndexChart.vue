<template>
  <div class="page-header-index-wide">
    <a-row :gutter="24">
      <a-col :sm="24" :md="12" :xl="6" :style="{ marginBottom: '24px' }">
        <chart-card :loading="loading" title="总投诉量" total="1520">
          <a-tooltip title="指标说明" slot="action">
            <a-icon type="info-circle-o" />
          </a-tooltip>
          <div>
            <trend flag="up" style="margin-right: 16px;">
              <span slot="term">周同比</span>
              12%
            </trend>
            <trend flag="down">
              <span slot="term">日同比</span>
              11%
            </trend>
          </div>
          <template slot="footer">日均投诉量<span>10</span></template>
        </chart-card>
      </a-col>
      <a-col :sm="24" :md="12" :xl="6" :style="{ marginBottom: '24px' }">
        <chart-card :loading="loading" title="当前城市AQI" :total="hzWeatherInfo.aqi">
          <a-tooltip title="指标说明" slot="action">
            <a-icon type="info-circle-o" />
          </a-tooltip>
          <div> 
            <span slot="term">PM2.5指数：{{hzWeatherInfo.pm2_5}}</span>
          </div>
          <div> 
            <span slot="term">质量评级：</span><span :style="{'color':hzWeatherInfo.color,'font-weight':'bold'}"> {{hzWeatherInfo.quality}}</span>
          </div>
          <template slot="footer">更新时间<span> {{hzWeatherInfo.time}}</span></template>
        </chart-card>
      </a-col>
      <a-col :sm="24" :md="12" :xl="6" :style="{ marginBottom: '24px' }">
        <chart-card :loading="loading" title="企业处理平均时长（天）" :total="2.35">
          <a-tooltip title="指标说明" slot="action">
            <a-icon type="info-circle-o" />
          </a-tooltip>
          <div>
            <mini-bar :height="40" />
          </div>
          <template slot="footer">企业处理率 <span>95%</span></template>
        </chart-card>
      </a-col>
      <a-col :sm="24" :md="12" :xl="6" :style="{ marginBottom: '24px' }">
        <chart-card :loading="loading" title="投诉满意率" total="78%">
          <a-tooltip title="指标说明" slot="action">
            <a-icon type="info-circle-o" />
          </a-tooltip>
          <div>
            <mini-progress color="rgb(19, 194, 194)" :target="80" :percentage="78" :height="8" />
          </div>
          <template slot="footer">
            <trend flag="down" style="margin-right: 16px;">
              <span slot="term">同周比</span>
              12%
            </trend>
            <trend flag="up">
              <span slot="term">日环比</span>
              80%
            </trend>
          </template>
        </chart-card>
      </a-col>
    </a-row>

    <a-card :loading="loading" :bordered="false" :body-style="{padding: '0'}">
      <div class="salesCard">
        <a-tabs default-active-key="1" size="large" :tab-bar-style="{marginBottom: '24px', paddingLeft: '16px'}">

          <a-tab-pane loading="true" tab="投诉量" key="1">
            <a-row>
              <a-col :xl="16" :lg="12" :md="12" :sm="24" :xs="24">
                <bar title="投诉量排行" :dataSource="barData"/>
              </a-col>
              <a-col :xl="8" :lg="12" :md="12" :sm="24" :xs="24">
                <rank-list title="投诉量排行榜" :list="rankList1"/>
              </a-col>
            </a-row>
          </a-tab-pane>
          <a-tab-pane tab="投诉趋势" key="2">
            <a-row>
              <a-col :xl="16" :lg="12" :md="12" :sm="24" :xs="24">
                <line-chart-multid title="投诉趋势" :fields="complaintFields" :dataSource="complaintInfo"></line-chart-multid>
              </a-col>
              <a-col :xl="8" :lg="12" :md="12" :sm="24" :xs="24">
                <rank-list title="投诉地区排行榜" :list="rankList2"/>
              </a-col>
            </a-row>
          </a-tab-pane>
        </a-tabs>
      </div>
    </a-card>

    <a-row>
      <a-col :span="24">
        <a-card :loading="loading" :bordered="false" title="最近一周访问量统计" :style="{ marginTop: '24px' }">
          <a-row>
            <a-col :span="6">
              <head-info title="今日IP" :content="loginfo.todayIp"></head-info>
            </a-col>
            <a-col :span="2">
              <a-spin class='circle-cust'>
                <a-icon slot="indicator" type="environment" style="font-size: 24px"  />
              </a-spin>
            </a-col>
            <a-col :span="6">
              <head-info title="今日访问" :content="loginfo.todayVisitCount"></head-info>
            </a-col>
            <a-col :span="2">
              <a-spin class='circle-cust'>
                <a-icon slot="indicator" type="team" style="font-size: 24px"  />
              </a-spin>
            </a-col>
            <a-col :span="6">
              <head-info title="总访问量" :content="loginfo.totalVisitCount"></head-info>
            </a-col>
            <a-col :span="2">
              <a-spin class='circle-cust'>
                <a-icon slot="indicator" type="rise" style="font-size: 24px"  />
              </a-spin>
            </a-col>
          </a-row>
          <line-chart-multid :fields="visitFields" :dataSource="visitInfo"></line-chart-multid>
        </a-card>
      </a-col>
    </a-row>
  </div>
</template>

<script>
  import ChartCard from '@/components/ChartCard'
  import ACol from "ant-design-vue/es/grid/Col"
  import ATooltip from "ant-design-vue/es/tooltip/Tooltip"
  import MiniArea from '@/components/chart/MiniArea'
  import MiniBar from '@/components/chart/MiniBar'
  import MiniProgress from '@/components/chart/MiniProgress'
  import RankList from '@/components/chart/RankList'
  import Bar from '@/components/chart/Bar'
  import LineChartMultid from '@/components/chart/LineChartMultid'
  import HeadInfo from '@/components/tools/HeadInfo.vue'

  import Trend from '@/components/Trend'
  import { getLoginfo,getVisitInfo } from '@/api/api'

  const rankList1 = [{name:"杭州电子科技大学",total:"13"},{name:"浙江大学",total:"13"}]
  const rankList2 = [{name:"温州",total:"32"},{name:"杭州",total:"25"},{name:"无锡",total:"15"},{name:"新疆",total:"10"}]


  export default {
    name: "IndexChart",
    components: {
      ATooltip,
      ACol,
      ChartCard,
      MiniArea,
      MiniBar,
      MiniProgress,
      RankList,
      Bar,
      Trend,
      LineChartMultid,
      HeadInfo
    },
    data() {
      return {
        hzWeatherInfo:{},
        barData:[],
        complaintInfo:[{"企业投诉":12,"车辆投诉":15,tian:"2021-07-05",type: "07-05"}
        ,{"企业投诉":15,"车辆投诉":11,tian: "2021-07-06",type: "07-06"}
        ,{"企业投诉":12,"车辆投诉":13,tian: "2021-07-07",type: "07-07"}],
        complaintFields:['企业投诉','车辆投诉'],
        loading: true,
        center: null,
        rankList1,
        rankList2, 
        loginfo:{},
        visitFields:['ip','visit'],
        visitInfo:[],
        indicator: <a-icon type="loading" style="font-size: 24px" spin />
      }
    },
    created() {
      this.$axios({
        method:'get',									
        url:'http://api.tianapi.com/txapi/aqi/index',
        params: {      
            key:"60374cf0ae273370a18b138f8ce6e4b2",
            area:"杭州"
        }
        }).then((response) =>{          //返回promise(ES6语法)
          this.hzWeatherInfo=response.data.newslist[0]
          if(this.hzWeatherInfo.quality=="优质"){
          this.hzWeatherInfo.color="#32CD32"
      }
            console.log(this.hzWeatherInfo) 
        }).catch((error) =>{
            console.log(error)       //请求失败返回的数据
        }),

      setTimeout(() => {
        this.loading = !this.loading
      }, 1000)
      this.initLogInfo();
    },

      mounted() {
      this.$axios({
        method:'get',									
        url:'http://182.92.125.128:8080/azurecity/pollutionComplaints/pollutionComplaints/getComplimentData',
        // params: {      
        //     key:"60374cf0ae273370a18b138f8ce6e4b2",
        //     area:"杭州"
        // }
        }).then((response) =>{          //返回promise(ES6语法)
          for (let i = 0; i < 8; i += 1) {
            this.barData.push({
              x: response.data.result[i].date,
              y: response.data.result[i].complaintNum
            })
          }
          console.log("barData:"+response.data.result) 
        }).catch((error) =>{
            console.log(error)       //请求失败返回的数据
        })
    },

    methods: {
      initLogInfo () {
        getLoginfo(null).then((res)=>{
          if(res.success){
            Object.keys(res.result).forEach(key=>{
              res.result[key] =res.result[key]+""
            })
            this.loginfo = res.result;
          }
        })
        getVisitInfo().then(res=>{
          if(res.success){
             this.visitInfo = res.result;
             console.log(this.visitInfo);
           }
         })
      },
    },
  }
</script>

<style lang="less" scoped>
  .circle-cust{
    position: relative;
    top: 28px;
    left: -100%;
  }
  .extra-wrapper {
    line-height: 55px;
    padding-right: 24px;

    .extra-item {
      display: inline-block;
      margin-right: 24px;

      a {
        margin-left: 24px;
      }
    }
  }

  /* 首页访问量统计 */
  .head-info {
    position: relative;
    text-align: left;
    padding: 0 32px 0 0;
    min-width: 125px;

    &.center {
      text-align: center;
      padding: 0 32px;
    }

    span {
      color: rgba(0, 0, 0, .45);
      display: inline-block;
      font-size: .95rem;
      line-height: 42px;
      margin-bottom: 4px;
    }
    p {
      line-height: 42px;
      margin: 0;
      a {
        font-weight: 600;
        font-size: 1rem;
      }
    }
  }
</style>