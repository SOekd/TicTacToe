<template>
  <v-container class="pt-8 min-h-screen min-w-screen">
    <v-container class="justify-center">
      <v-row class="justify-space-between align-center">
        <v-col cols="auto" align-self="start">
          <v-btn variant="tonal" color="red">Sair</v-btn>
        </v-col>
        <v-col cols="auto" align-self="end">
          <v-label style="font-size: 5vw;">SENHA: {{game.otp}}</v-label>
        </v-col>
      </v-row>

    </v-container>
    <v-card>
      <v-card-title class="text-center">
        <h2 style="font-size: 6vw;">ADVERSÁRIO</h2>
      </v-card-title>
      <v-row class="pa-7">
        <v-col cols="auto" align-self="center">
          <p style="font-size: 6vw;">{{ getOpponentName() == null ? 'Aguardando o oponente!' : getOpponentName() }}</p>
        </v-col>
      </v-row>
    </v-card>
  </v-container>

  <v-spacer></v-spacer>

  <v-container class="flex flex-column">
    <v-row no-gutters>
      <v-col cols="4"
             class="justify-center align-center"
             style="border-right: white 1px solid; border-bottom: white 1px solid; aspect-ratio: 1/1; width: 33%">
        <v-btn width="100%" height="100%" style="font-size: 9vw;"><h1>A</h1></v-btn>
      </v-col>
      <v-col cols="4"
             style="border-bottom: white 1px solid; aspect-ratio: 1/1; width: 33%">
        <v-btn width="100%" height="100%" style="font-size: 9vw;"><h1>A</h1></v-btn>
      </v-col>
      <v-col cols="4"
             style="border-left: white 1px solid; border-bottom: white 1px solid; aspect-ratio: 1/1; width: 33%">
        <v-btn width="100%" height="100%" style="font-size: 9vw;"><h1>A</h1></v-btn>
      </v-col>
    </v-row>
    <v-row no-gutters>
      <v-col cols="4"
             style="border-right: white 1px solid; aspect-ratio: 1/1; width: 33%">
        <v-btn width="100%" height="100%" style="font-size: 9vw;"><h1>A</h1></v-btn>
      </v-col>
      <v-col cols="4"
             style="aspect-ratio: 1/1; width: 33%">
        <v-btn width="100%" height="100%" style="font-size: 9vw;"><h1>A</h1></v-btn>
      </v-col>
      <v-col cols="4"
             style="border-left: white 1px solid; aspect-ratio: 1/1; width: 33%">
        <v-btn width="100%" height="100%" style="font-size: 9vw;"><h1>A</h1></v-btn>
      </v-col>
    </v-row>
    <v-row no-gutters>
      <v-col cols="4"
             style="border-right: white 1px solid;border-top: white 1px solid;aspect-ratio: 1/1; width: 33%">
        <v-btn width="100%" height="100%" style="font-size: 9vw;"><h1>A</h1></v-btn>
      </v-col>
      <v-col cols="4"
             style="border-top: white 1px solid; aspect-ratio: 1/1; width: 33%">
        <v-btn width="100%" height="100%" style="font-size: 9vw;"><h1>A</h1></v-btn>
      </v-col>
      <v-col cols="4"
             style="border-left: white 1px solid; border-top: white 1px solid; aspect-ratio: 1/1; width: 33%">
        <v-btn width="100%" height="100%" style="font-size: 9vw;"><h1>A</h1></v-btn>
      </v-col>
    </v-row>

  </v-container>

  <v-spacer></v-spacer>

  <v-container>
    <v-card>
      <v-card-title class="text-center">
        <h2 style="font-size: 6vw;">VOCÊ</h2>
      </v-card-title>
      <v-row class="pa-7">
        <v-col cols="auto">
          <h2 style="font-size: 6vw;">{{ playerName }}</h2>
        </v-col>
      </v-row>
    </v-card>
  </v-container>
</template>


<script setup>
import Stomp from 'webstomp-client'

import {onUnmounted, ref} from 'vue';
import {useNameStorage} from "@/stores/nameStorage";
import gameService from "@/api/service/GameService";
import {useRoute} from "vue-router";

const route = useRoute()
const joined = ref(false)

const nameStorage = useNameStorage()

// eslint-disable-next-line no-undef
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
