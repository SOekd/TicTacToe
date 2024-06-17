<template>
  <v-container>
    <v-sheet class="pa-6">
      <v-container >
        <v-row justify="center">
          <v-col cols="auto">
            <h2>Jubilados Jubilam Juntos</h2>
          </v-col>
        </v-row>
        <v-row justify="center">
          <v-col cols="auto">
            <v-btn size="x-large" variant="tonal" color="green" @click="createNewGame">
              Criar Partida
            </v-btn>
          </v-col>
        </v-row >
        <v-row justify="center">
          <v-col cols="auto">
            <v-btn size="x-large" variant="tonal" color="blue">
              Entrar em partida
            </v-btn>
          </v-col>
        </v-row>
        <v-row justify="center">
          <v-col cols="auto">
            <v-btn size="x-large" variant="tonal" color="yellow">
              Opções
            </v-btn>
          </v-col>
        </v-row>
        <v-row justify="center">
          <v-col cols="auto">
            <v-btn size="x-large" variant="tonal" color="red">
              Sair
            </v-btn>
          </v-col>
        </v-row>
      </v-container>
    </v-sheet>
  </v-container>

</template>

<script setup>
import gameService from "@/api/service/GameService";
import {useRouter} from "vue-router";

const router = useRouter()

function createNewGame() {
  gameService.createGame(true).then(response => {
    router.push("/game/" + response.data.id)
  }).catch(error => {
    console.log(error)
  })
}

async function joinGameByOtp(otp) {
  gameService.getGameByOtp(otp)
    .then(response => {
      console.log(response)
    })
    .catch(error => { console.log(error) })
}
</script>

<style scoped lang="scss">



</style>
