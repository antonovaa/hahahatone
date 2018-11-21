<template>
  <div id="general">
    <component v-bind:is="this.currentView" :number="cvn" >    </component>

  </div>
</template>

<script>

  import GameLog from './component/GameLog.vue'
  import Gamers from './component/Gamers.vue'
  import Games from './component/Games.vue'


  export default {
    data() {
      return {
        temp:'',
        cvn:''
      }
    },
    components:{
      'game-log':GameLog,
      'gamers':Gamers,
      'games':Games,
    },
    computed:{
      currentView:function() {
        this.cvn=this.$store.state.currentViewNumber;
        if (this.cvn === 1) {return 'games';}
        if (this.cvn === 2) {return 'gamers';}
        if (this.cvn === 3) {return 'game-log';}
      }
    }
  }
</script>

<style>


</style>
