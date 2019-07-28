<template>
  <div id="general ">
    <component v-bind:is="this.currentView" :number="cvn" >    </component>




  </div>
</template>

<script>

  import Inputs from './component/Inputs.vue'


  export default {
    data() {
      return {
        temp:'',
        cvn:''
      }
    },
    components:{
      'inputs':Inputs,
    },
    computed:{
      currentView:function() {
        this.cvn=this.$store.state.currentViewNumber;
        if (this.cvn === 1) {return 'inputs';}

      }
    }
  }
</script>

<style>
  #general {

  }


</style>
