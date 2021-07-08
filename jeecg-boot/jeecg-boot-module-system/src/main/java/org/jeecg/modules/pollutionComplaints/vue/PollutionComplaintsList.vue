<template>
  <a-card :bordered="false">
    <!-- 查询区域 -->
    <div class="table-page-search-wrapper">
      <a-form layout="inline" @keyup.enter.native="searchQuery">
        <a-row :gutter="24">
          <a-col :xl="6" :lg="7" :md="8" :sm="24">
            <a-form-item label="投诉对象名称">
              <a-input placeholder="请输入投诉对象名称" v-model="queryParam.cptObject"></a-input>
            </a-form-item>
          </a-col>
          <a-col :xl="6" :lg="7" :md="8" :sm="24">
            <span style="float: left;overflow: hidden;" class="table-page-search-submitButtons">
              <a-button type="primary" @click="searchQuery" icon="search">查询</a-button>
              <a-button type="primary" @click="searchReset" icon="reload" style="margin-left: 8px">重置</a-button>
              <a @click="handleToggleSearch" style="margin-left: 8px">
                {{ toggleSearchStatus ? '收起' : '展开' }}
                <a-icon :type="toggleSearchStatus ? 'up' : 'down'"/>
              </a>
            </span>
          </a-col>
        </a-row>
      </a-form>
    </div>
    <!-- 查询区域-END -->

    <!-- 操作按钮区域 -->
    <div class="table-operator">
      <a-button @click="handleAdd" type="primary" icon="plus">新增</a-button>
      <a-button type="primary" icon="download" @click="handleExportXls('污染投诉表')">导出</a-button>
      <a-upload name="file" :showUploadList="false" :multiple="false" :headers="tokenHeader" :action="importExcelUrl" @change="handleImportExcel">
        <a-button type="primary" icon="import">导入</a-button>
      </a-upload>
      <!-- 高级查询区域 -->
      <j-super-query :fieldList="superFieldList" ref="superQueryModal" @handleSuperQuery="handleSuperQuery"></j-super-query>
      <a-dropdown v-if="selectedRowKeys.length > 0">
        <a-menu slot="overlay">
          <a-menu-item key="1" @click="batchDel"><a-icon type="delete"/>删除</a-menu-item>
        </a-menu>
        <a-button style="margin-left: 8px"> 批量操作 <a-icon type="down" /></a-button>
      </a-dropdown>
    </div>

    <!-- table区域-begin -->
    <div>
      <div class="ant-alert ant-alert-info" style="margin-bottom: 16px;">
        <i class="anticon anticon-info-circle ant-alert-icon"></i> 已选择 <a style="font-weight: 600">{{ selectedRowKeys.length }}</a>项
        <a style="margin-left: 24px" @click="onClearSelected">清空</a>
      </div>

      <a-table
        ref="table"
        size="middle"
        :scroll="{x:true}"
        bordered
        rowKey="id"
        :columns="columns"
        :dataSource="dataSource"
        :pagination="ipagination"
        :loading="loading"
        :rowSelection="{selectedRowKeys: selectedRowKeys, onChange: onSelectChange}"
        class="j-table-force-nowrap"
        @change="handleTableChange">

        <template slot="htmlSlot" slot-scope="text">
          <div v-html="text"></div>
        </template>
        <template slot="imgSlot" slot-scope="text">
          <span v-if="!text" style="font-size: 12px;font-style: italic;">无图片</span>
          <img v-else :src="getImgView(text)" height="25px" alt="" style="max-width:80px;font-size: 12px;font-style: italic;"/>
        </template>
        <template slot="fileSlot" slot-scope="text">
          <span v-if="!text" style="font-size: 12px;font-style: italic;">无文件</span>
          <a-button
            v-else
            :ghost="true"
            type="primary"
            icon="download"
            size="small"
            @click="downloadFile(text)">
            下载
          </a-button>
        </template>

        <span slot="action" slot-scope="text, record">
          <a @click="handleEdit(record)">编辑</a>

          <a-divider type="vertical" />
          <a-dropdown>
            <a class="ant-dropdown-link">更多 <a-icon type="down" /></a>
            <a-menu slot="overlay">
              <a-menu-item>
                <a @click="handleDetail(record)">详情</a>
              </a-menu-item>
              <a-menu-item>
                <a-popconfirm title="确定删除吗?" @confirm="() => handleDelete(record.id)">
                  <a>删除</a>
                </a-popconfirm>
              </a-menu-item>
            </a-menu>
          </a-dropdown>
        </span>

      </a-table>
    </div>

    <pollution-complaints-modal ref="modalForm" @ok="modalFormOk"></pollution-complaints-modal>
  </a-card>
</template>

<script>

  import '@/assets/less/TableExpand.less'
  import { mixinDevice } from '@/utils/mixin'
  import { JeecgListMixin } from '@/mixins/JeecgListMixin'
  import PollutionComplaintsModal from './modules/PollutionComplaintsModal'
  import {filterMultiDictText} from '@/components/dict/JDictSelectUtil'

  export default {
    name: 'PollutionComplaintsList',
    mixins:[JeecgListMixin, mixinDevice],
    components: {
      PollutionComplaintsModal
    },
    data () {
      return {
        description: '污染投诉表管理页面',
        // 表头
        columns: [
          {
            title: '#',
            dataIndex: '',
            key:'rowIndex',
            width:60,
            align:"center",
            customRender:function (t,r,index) {
              return parseInt(index)+1;
            }
          },
          {
            title:'投诉对象名称',
            align:"center",
            dataIndex: 'cptObject'
          },
          {
            title:'投诉描述 ',
            align:"center",
            dataIndex: 'cptDescribe'
          },
          {
            title:'投诉图片',
            align:"center",
            dataIndex: 'cptPic',
            scopedSlots: {customRender: 'imgSlot'}
          },
          {
            title:'投诉状态',
            align:"center",
            dataIndex: 'cptState_dictText'
          },
          {
            title:'污染投诉类型',
            align:"center",
            dataIndex: 'cptType'
          },
          {
            title:'投诉简要位置',
            align:"center",
            dataIndex: 'cptPositionBrief'
          },
          {
            title:'投诉详细位置',
            align:"center",
            dataIndex: 'cptPositionDetailil'
          },
          {
            title:'投诉位置经度',
            align:"center",
            dataIndex: 'cptPositionLongitude'
          },
          {
            title:'投诉位置纬度',
            align:"center",
            dataIndex: 'cptPositionLatitude'
          },
          {
            title:'投诉提交日期',
            align:"center",
            dataIndex: 'cptSubmitTime',
            customRender:function (text) {
              return !text?"":(text.length>10?text.substr(0,10):text)
            }
          },
          {
            title: '操作',
            dataIndex: 'action',
            align:"center",
            fixed:"right",
            width:147,
            scopedSlots: { customRender: 'action' }
          }
        ],
        url: {
          list: "/pollutionComplaints/pollutionComplaints/list",
          delete: "/pollutionComplaints/pollutionComplaints/delete",
          deleteBatch: "/pollutionComplaints/pollutionComplaints/deleteBatch",
          exportXlsUrl: "/pollutionComplaints/pollutionComplaints/exportXls",
          importExcelUrl: "pollutionComplaints/pollutionComplaints/importExcel",
          
        },
        dictOptions:{},
        superFieldList:[],
      }
    },
    created() {
    this.getSuperFieldList();
    },
    computed: {
      importExcelUrl: function(){
        return `${window._CONFIG['domianURL']}/${this.url.importExcelUrl}`;
      },
    },
    methods: {
      initDictConfig(){
      },
      getSuperFieldList(){
        let fieldList=[];
        fieldList.push({type:'string',value:'cptObject',text:'投诉对象名称',dictCode:''})
        fieldList.push({type:'string',value:'cptDescribe',text:'投诉描述 ',dictCode:''})
        fieldList.push({type:'string',value:'cptPic',text:'投诉图片',dictCode:''})
        fieldList.push({type:'string',value:'cptState',text:'投诉状态',dictCode:'complaint_code'})
        fieldList.push({type:'string',value:'cptType',text:'污染投诉类型',dictCode:'complain_type'})
        fieldList.push({type:'string',value:'cptPositionBrief',text:'投诉简要位置',dictCode:''})
        fieldList.push({type:'string',value:'cptPositionDetailil',text:'投诉详细位置',dictCode:''})
        fieldList.push({type:'string',value:'cptPositionLongitude',text:'投诉位置经度',dictCode:''})
        fieldList.push({type:'string',value:'cptPositionLatitude',text:'投诉位置纬度',dictCode:''})
        fieldList.push({type:'date',value:'cptSubmitTime',text:'投诉提交日期'})
        this.superFieldList = fieldList
      }
    }
  }
</script>
<style scoped>
  @import '~@assets/less/common.less';
</style>