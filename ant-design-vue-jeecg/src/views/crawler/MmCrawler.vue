
<template>
  <a-card :bordered="false">
    <a-row>
<!--      <a-col :md="2" :sm="4">-->
<!--        <a-select defaultValue="mm" style="width: 90px" @change="handleChange" size="large">-->
<!--          <a-select-option value="mm">脉脉</a-select-option>-->
<!--          <a-select-option value="wx">微信</a-select-option>-->
<!--        </a-select>-->

<!--      </a-col>-->


      <a-col :md="22" :sm="20">
        <a-input-search
          placeholder="格式如下：1-100"
          v-model="params"
          @search="onSearch"
          enterButton="开爬"
          size="large" />
      </a-col>
    </a-row>

<!--    <a-tabs defaultActiveKey="2">-->
<!--      <a-tab-pane tab="params" key="2">-->
<!--        <textarea style="width:100%;font-size: 16px;font-weight:500" :rows="13" @input="changeVal">-->
<!--        </textarea>-->
<!--      </a-tab-pane>-->
<!--    </a-tabs>-->

    <a-tabs defaultActiveKey="1">
      <a-tab-pane tab="response" key="1">
        <textarea style="width:100%;font-size: 16px;font-weight:500" :rows="10" v-html="resultJson" readonly>
        </textarea>
      </a-tab-pane>
    </a-tabs>
  </a-card>
</template>
<script>
  import { postAction,getAction } from '@/api/manage'
  import { ACCESS_TOKEN } from "@/store/mutation-types"
  import Vue from 'vue'
  export default {
    name: 'MmCrawler',
    data(){
      return {
        params:"",
        resultJson:{}
      }
    },
    methods: {
      onSearch (value) {
        let that = this
        this.resultJson = {};
        postAction('/crawler/base', this.params).then((res)=>{
          console.log(res)
          this.resultJson = res
        }).catch((err) => {
          that.$message.error("请求异常："+err)
        })
      },
      created () {
        const token = Vue.ls.get(ACCESS_TOKEN);
        this.headers = {"X-Access-Token":token}
      }
    }
  }
</script>