<template>
  <v-container>
    <v-btn variant="tonal" color="red">Sair</v-btn>
  </v-container>

  <v-container>
    <v-card>
      <v-card-title>Adversario</v-card-title>
      <v-row class="pa-7">
        <v-col cols="auto">
          <h2>{{ getOpponentName() == null ? 'Aguardando o oponente!' : getOpponentName() }}</h2>
        </v-col>
      </v-row>
    </v-card>
  </v-container>

  <v-spacer></v-spacer>

  <v-container>
    <v-row no-gutters>
      <v-col cols="4" class="fill-height">
          <v-btn variant="text">
            posição 1
          </v-btn>
      </v-col>
      <v-divider vertical></v-divider>
      <v-col cols="4">
        <v-btn variant="text">
          posição 2
        </v-btn>
      </v-col>
      <v-divider vertical></v-divider>
      <v-col cols="4">
        <v-btn variant="text">
          posição 3
        </v-btn>
      </v-col>
    </v-row>

    <v-divider></v-divider>

    <v-row no-gutters>


      <v-col cols="4">
        <v-btn variant="text">
          posição 4
        </v-btn>
      </v-col>
      <v-divider vertical></v-divider>
      <v-col cols="4">
        <v-btn variant="text">
          posição 5
        </v-btn>
      </v-col>
      <v-divider vertical></v-divider>
      <v-col cols="4">
        <v-btn variant="text">
          posição 6
        </v-btn>
      </v-col>
    </v-row>

    <v-divider></v-divider>

    <v-row no-gutters>
      <v-col cols="4">
        <v-btn variant="text">
          posição 7
        </v-btn>
      </v-col>
      <v-divider vertical></v-divider>
      <v-col cols="4">
        <v-btn variant="text">
          posição 8
        </v-btn>
      </v-col>
      <v-divider vertical></v-divider>
      <v-col cols="4">
        <v-btn variant="text">
          posição 9
        </v-btn>
      </v-col>
    </v-row>

  </v-container>

  <v-spacer></v-spacer>

  <v-container>
    <v-card>
      <v-card-title>Você</v-card-title>
      <v-row class="pa-7">
        <v-col cols="auto">
          <h2>{{ playerName }}</h2>
        </v-col>
      </v-row>
    </v-card>
  </v-container>
</template>


<script setup>
import Stomp from 'webstomp-client'

import {onUnmounted, onBeforeMount, ref} from 'vue';
import {useNameStorage} from "@/stores/nameStorage";
import gameService from "@/api/service/GameService";
import {useRoute} from "vue-router";

const route = useRoute()
const joined = ref(false)


const nameStorage = useNameStorage()

const game = reactive({
  id: '',
  created: null,
  otp: '',
  publicGame: false,
  playerOne: null,
  playerTwo: null,
  currentPlayer: null,
  board: [
    ['', '', ''],
    ['', '', '']
  ],
  gameState: '',
})

const playerName = ref(nameStorage.getPlayerName())
const token = ref('')

const apiBaseUrl = import.meta.env.VITE_WEBSOCKET_URL;

gameService.getValidToken().then((responseToken) => {
  token.value = responseToken
})

const socket = Stomp.client(apiBaseUrl)

socket.connect({}, () => {
  socket.subscribe(`/response/move/${route.params.id}`, data => {
    console.log("Received: " + data.body)
  })

  socket.subscribe(`/response/join/${route.params.id}`, data => {
    console.log("Joined: " + data.body)
    joined.value = true
    parseGame(data.body)

    console.log("Opponent: " + getOpponentName())
  })

  sendJoinRequest()
})

function parseGame(jsonGame) {
  const parsedJson = JSON.parse(jsonGame)

  game.id = parsedJson.id
  game.created = parsedJson.created
  game.otp = parsedJson.otp
  game.publicGame = parsedJson.publicGame
  game.playerOne = parsedJson.playerOne
  game.playerTwo = parsedJson.playerTwo
  game.currentPlayer = parsedJson.currentPlayer
  game.board = parsedJson.board
  game.gameState = parsedJson.gameState
}

function sendJoinRequest() {

  console.log("Sending join request")
  console.log("Token:" + token)
  console.log("Player:" + playerName)

  socket.send(`/request/join/${route.params.id}`, JSON.stringify({
    playerToken: token.value,
    playerName: playerName.value
  }))
}

onUnmounted(() => {
  socket.disconnect()
})
function getOpponentName() {
  if (game.playerOne === playerName.value) {
    return game.playerTwo
  } else {
    return game.playerOne
  }
}

function sendMoveRequest(x, y) {

  console.log("Sending move request")

  socket.send(`/request/move/${route.params.id}`, JSON.stringify({
    playerToken: token.value,
    x: x,
    y: y
  }))

}

</script>

<style scoped lang="sass">

</style>
