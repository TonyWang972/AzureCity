<template>
  <a-spin :spinning="confirmLoading">
    <j-form-container :disabled="formDisabled">
      <a-form-model ref="form" :model="model" :rules="validatorRules" slot="detail">
        <a-row>
          <a-col :span="24">
            <a-form-model-item label="投诉对象名称" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="cptObject">
              <a-input v-model="model.cptObject" placeholder="请输入投诉对象名称"  ></a-input>
            </a-form-model-item>
          </a-col>
          <a-col :span="24">
            <a-form-model-item label="投诉描述 " :labelCol="labelCol" :wrapperCol="wrapperCol" prop="cptDescribe">
              <a-input v-model="model.cptDescribe" placeholder="请输入投诉描述 "  ></a-input>
            </a-form-model-item>
          </a-col>
          <a-col :span="24">
            <a-form-model-item label="投诉图片" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="cptPic">
              <j-image-upload isMultiple  v-model="model.cptPic" ></j-image-upload>
            </a-form-model-item>
          </a-col>
          <a-col :span="24">
            <a-form-model-item label="投诉状态" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="cptState">
              <j-dict-select-tag type="list" v-model="model.cptState" dictCode="complaint_code" placeholder="请选择投诉状态" />
            </a-form-model-item>
          </a-col>
          <a-col :span="24">
            <a-form-model-item label="污染投诉类型" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="cptType">
              <a-input v-model="model.cptType" placeholder="请输入污染投诉类型"  ></a-input>
            </a-form-model-item>
          </a-col>
          <a-col :span="24">
            <a-form-model-item label="投诉简要位置" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="cptPositionBrief">
              <a-input v-model="model.cptPositionBrief" placeholder="请输入投诉简要位置"  ></a-input>
            </a-form-model-item>
          </a-col>
          <a-col :span="24">
            <a-form-model-item label="投诉详细位置" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="cptPositionDetailil">
              <a-input v-model="model.cptPositionDetailil" placeholder="请输入投诉详细位置"  ></a-input>
            </a-form-model-item>
          </a-col>
          <a-col :span="24">
            <a-form-model-item label="投诉位置经度" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="cptPositionLongitude">
              <a-input v-model="model.cptPositionLongitude" placeholder="请输入投诉位置经度"  ></a-input>
            </a-form-model-item>
          </a-col>
          <a-col :span="24">
            <a-form-model-item label="投诉位置纬度" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="cptPositionLatitude">
              <a-input v-model="model.cptPositionLatitude" placeholder="请输入投诉位置纬度"  ></a-input>
            </a-form-model-item>
          </a-col>
          <a-col :span="24">
            <a-form-model-item label="投诉提交日期" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="cptSubmitTime">
              <j-date placeholder="请选择投诉提交日期" v-model="model.cptSubmitTime"  style="width: 100%" />
            </a-form-model-item>
          </a-col>
        </a-row>
      </a-form-model>
    </j-form-container>
  </a-spin>
</template>

<script>

  import { httpAction, getAction } from '@/api/manage'
  import { validateDuplicateValue } from '@/utils/util'

  export default {
    name: 'PollutionComplaintsForm',
    components: {
    },
    props: {
      //表单禁用
      disabled: {
        type: Boolean,
        default: false,
        required: false
      }
    },
    data () {
      return {
        model:{
            cptObject:"（企业或车牌 ）",
         },
        labelCol: {
          xs: { span: 24 },
          sm: { span: 5 },
        },
        wrapperCol: {
          xs: { span: 24 },
          sm: { span: 16 },
        },
        confirmLoading: false,
        validatorRules: {
        },
        url: {
          add: "/pollutionComplaints/pollutionComplaints/add",
          edit: "/pollutionComplaints/pollutionComplaints/edit",
          queryById: "/pollutionComplaints/pollutionComplaints/queryById"
        }
      }
    },
    computed: {
      formDisabled(){
        return this.disabled
      },
    },
    created () {
       //备份model原始值
      this.modelDefault = JSON.parse(JSON.stringify(this.model));
    },
    methods: {
      add () {
        this.edit(this.modelDefault);
      },
      edit (record) {
        this.model = Object.assign({}, record);
        this.visible = true;
      },
      submitForm () {
        const that = this;
        // 触发表单验证
        this.$refs.form.validate(valid => {
          if (valid) {
            that.confirmLoading = true;
            let httpurl = '';
            let method = '';
            if(!this.model.id){
              httpurl+=this.url.add;
              method = 'post';
            }else{
              httpurl+=this.url.edit;
               method = 'put';
            }
            httpAction(httpurl,this.model,method).then((res)=>{
              if(res.success){
                that.$message.success(res.message);
                that.$emit('ok');
              }else{
                that.$message.warning(res.message);
              }
            }).finally(() => {
              that.confirmLoading = false;
            })
          }
         
        })
      },
    }
  }
</script>