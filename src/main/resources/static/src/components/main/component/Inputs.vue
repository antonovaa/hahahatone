<template>
  <div id="inputs">

    <h1>Поиск по интерактивной базе знаний</h1>
    <!--<p>Количество совпадений: {{countApp}}</p>-->
    <br/>
    <p>поиск {{application}}</p>
    <input  v-model="application"/>
    <br/>


    <div class="container-fluid" >
    <div class="row" >



    <div style="height: 700px; overflow-y: scroll;" class="col-md-6 backimage">
      <p>
      выберите из тегов
    </p><table class="table table-dark">

      <thead>
      <tr>
        <th scope="col">№</th>
        <th scope="col">TagName</th>
        <th scope="col">Хештег</th>
        <th scope="col">Checked</th>
      </tr>
      </thead>
      <tbody>
      <tr v-for="(value, index) in rowsCur ">
        <td>{{index+1}}</td>
        <td>{{value.tags}}</td>
        <td>{{value.descr}}</td>
        <td>

          <div>
            <button class="btn btn-primary"
                    v-on:click="addTags(value.id,value.tags,value.descr)">добавить тег
            </button>
          </div>
        </td>
      </tr>
      </tbody>
    </table></div>

    <div class=" col-md-6">
      <div style="height: 300px; overflow-y: scroll;" class="">
        <div style="text-align: center">выбраные теги</div>

        <table class="table table-dark">

        <thead>
        <tr>
          <th scope="col">ID</th>
          <th scope="col">TagName</th>
          <th scope="col">Описание</th>
          <th scope="col">Checked</th>
        </tr>
        </thead>
        <tbody>
        <tr v-for="(value, index) in rowsChecked ">
          <td>{{value.id}}</td>
          <td>{{value.tags}}</td>
          <td>{{value.descr}}</td>
          <td>

            <div>
              <button class="btn btn-primary"
                      v-on:click="removeTags(value.id)">удалить тег
              </button>
            </div>
          </td>
        </tr>
        </tbody>
      </table>
      </div>
      <div style="height: 400px; overflow-y: scroll;">
        <p>
          возможные сценарии
        </p>
        <table style="" class="table table-dark">

          <thead>
          <tr>
            <th scope="col">ID</th>
            <th scope="col">Сценарий</th>
            <th scope="col">Описание</th>
            <th scope="col">Выбор</th>
          </tr>
          </thead>
          <tbody>
          <tr v-for="(value, index) in SuitableTags ">
            <td>{{value.id}}</td>
            <td>{{value.tags}}</td>
            <td>{{value.descr}}</td>
            <td>

              <div>
                <button class="btn btn-primary"
                        v-on:click="getStepsInScen(value.id)">выбрать сценарий
                </button>
              </div>
            </td>
          </tr>
          </tbody>
        </table>

      </div>

      </div>


  </div>


    <br/>
    <br/>
    <br/>

    <p>
      Результат сценария (уточнение)
    </p>

      <div class="row borda" >

        <table style="" class="table table-dark">

          <thead>
          <tr>
            <th scope="col">ID</th>
            <th scope="col">Тип</th>
            <th scope="col">описание</th>
          </tr>
          </thead>
          <tbody>
          <tr v-for="(value, index) in ScenSteps ">
            <td>{{value.no}}</td>
            <td>{{value.typ}}</td>
            <td>{{value.descr}}</td>
            <td>

              <div>
                <button style="width: 100px" class="btn btn-primary"
                        v-on:click="getSteps(value.id)">{{value.button}}
                </button>
              </div>
            </td>
          </tr>
          </tbody>
        </table>


      </div>



  </div>
  </div>
</template>

<script>


  export default {
    data() {
      return {
        application:'',
        rowsChecked:[],
        idChecked:[],
        rowsCur:[],
        ScenSteps:[],
        SuitableTags:[],
        rowsReqAction:[

          {id:1,tags:'ufp',descr:'qweqwe2 asda das d'},
          {id:2,tags:'газ',descr:'qweqwe233 asda sdas d'},
          {id:3,tags:'вода',descr:'qweqwe233 asda sdas d'},
          {id:4,tags:'канализ',descr:'qweqwe233 asda sdas d'},
          {id:5,tags:'свет',descr:'qweqwe233 asda sdas d'},
          {id:6,tags:'дым',descr:'qweqwe233 asda sdas d'},
          {id:7,tags:'шум',descr:'qweqwe233 asda sdas d'},
          {id:8,tags:'бум',descr:'qweqwe233 asda sdas d'},
          {id:9,tags:'влом',descr:'qweqwe233 asda sdas d'},

          ]

      }
    },
    created:function(){
      this.getTags();
    },
    methods:{
      addTags:function(id,tagsV,descrV){

        this.rowsChecked.push({id:id,tags:tagsV,descr:descrV});
        this.idChecked.push(id);
        let t=this.idChecked;
        this.rowsCur=this.rowsReqAction.filter(function(e) { return !t.includes(e.id) });

        this.getScen();
        this.application='';
        //todo request
      },
      removeTags:function(id){

        this.idChecked=this.idChecked.filter(function(e) { return e !== id });
        this.rowsChecked=this.rowsChecked.filter(function(e) { return e.id !== id });
        let t = this.idChecked;
        this.rowsCur = this.rowsReqAction.filter(function (e) {
            return !t.includes(e.id);});
        this.getScen();
      },



      //get all tags
      getTags:function () {

          axios.get('/getTags', {
        headers: {
          'Content-Type': 'application/json;charset=UTF-8'
        }
      })
        .then((req) => {
          this.rowsReqAction=req.data;
          this.rowsCur=this.rowsReqAction;
          console.log("success loaded "+req);
        })
        .catch((req) => {
          console.log("error loaded "+req)
        });
      },



      //get all scen by tags
      getScen:function () {
        let t= this.rowsChecked.map(function(va) {
          return va.id;
        });
        if(t.length!==0) {
          axios.get('/getScen?t=' + t, {
            headers: {
              'Content-Type': 'application/json;charset=UTF-8'
            }
          })
          .then((req) => {
            this.SuitableTags = req.data;
            console.log("success loaded " + req);
          })
          .catch((req) => {
            console.log("error loaded " + req)
          });
        }
        else
        {
          this.SuitableTags=[];
        }
      },


    //get all steps in scen
    getStepsInScen:function (id) {

      axios.get('/getStepsInScen?id='+id, {
        headers: {
          'Content-Type': 'application/json;charset=UTF-8'
        }
      })
      .then((req) => {
        this.ScenSteps=req.data;
        console.log("success loaded "+req);
      })
      .catch((req) => {
        console.log("error loaded "+req)
      });
    },
  },


    watch: {
      'application': function (val, preVal) {
        let sub;
        if (val !== '') {
          let t = this.idChecked;
          sub=val;
          if(val.length>4)
            sub=val.substring(0, val.length-1);
          if(val.length>5)
            sub=val.substring(0, val.length-2);

          this.rowsCur = this.rowsReqAction.filter(function (e) {
            return ((e.tags.includes(sub)||e.descr.includes(sub)) && !t.includes(e.id))
          });
        }
        else {
          let t = this.idChecked;
          this.rowsCur = this.rowsReqAction.filter(function (e) {
            return !t.includes(e.id)
          });
        }
      }
    }
  }
</script>


<!--<img src="../../header/images/tOF8eq8.jpg" height="1066" width="1600"/>-->
<style>
  .backimage {
    background: url("https://cstrainingsystems.com/wp-content/uploads/2016/06/lemur-syas-stop.jpg") no-repeat;
    background-size: cover;
  }
  .borda{
    height: 1100px;
    border: #0c5460  double ;
  }
</style>
