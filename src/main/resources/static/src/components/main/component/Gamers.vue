<template>
  <div id="gamers">
    <h1>Зарегистрированные иргоки</h1>
    <br/>
    <select v-model="gamesId">
      <option v-for="option in optionsGames" v-bind:value="option.games_id">
        {{ option.game_name }}
      </option>
    </select>
    <button class="btn" v-on:click="getGamersGames()">Отфильтровать</button>
    <table class="table table-dark">
      <thead>
      <tr>
        <th scope="col">id</th>
        <th scope="col">Game</th>
        <th scope="col">Login</th>
        <th scope="col">Password</th>
        <th scope="col">Email</th>
        <th scope="col">Date registration</th>
        <th scope="col">Player Status</th>
      </tr>
      </thead>
      <tbody>
      <tr v-for="(value, index) in gamersInfo">
        <td>{{value.registrated_id}}</td>
        <td>{{value.game_name}}</td>
        <td>{{value.login}}</td>
        <td>{{value.password}}</td>
        <td>{{value.email}}</td>
        <td>{{value.registrate_date}}</td>
        <td>{{value.info}}</td>
      </tr>
      </tbody>
    </table>
  </div>
</template>

<script>


  export default {
    data() {
      return {
        gamersInfo: [],
        gamesId:0,
        optionsGames:[]
      }
    },
    created: function () {

      axios.get('/getGames', {
        headers: {
          'Content-Type': 'application/json;charset=UTF-8'
        }
      })
      .then((req) => {
        this.optionsGames = req.data;
      })
      .catch((req) => {
        console.log("error loaded " + req)
      });

      this.getGamersGames();
    },
    methods: {
      getGamersGames: function () {
        axios.get('/getGamersGame?gamesId='+this.gamesId, {
          headers: {
            'Content-Type': 'application/json;charset=UTF-8'
          }
        })
        .then((req) => {
          this.gamersInfo = req.data;
          console.log("success loaded " + req);
        })
        .catch((req) => {
          console.log("error loaded " + req)
        });
      }
    }
  }
</script>

<style>


</style>
