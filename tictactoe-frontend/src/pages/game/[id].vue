<template>
  <v-container class="pt-8 min-h-screen min-w-screen">
    <v-container class="justify-center">
      <v-row class="justify-space-between align-center">
        <v-col cols="auto" align-self="start">
          <v-btn @click="leave" variant="tonal" color="red">Sair</v-btn>
        </v-col>
        <v-col cols="auto" align-self="center">
          <v-label style="font-size: 5vw;">Seu turno: {{ myTurn() ? "sim" : "não" }}</v-label>
        </v-col>
        <v-col cols="auto" align-self="end">
          <v-label style="font-size: 5vw;">SENHA: {{ game.otp }}</v-label>
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
        <v-btn width="100%" height="100%" style="font-size: 9vw;" @click="handleMove(1, 1)"><h1>{{
            game.board[0][0]
          }}</h1></v-btn>
      </v-col>
      <v-col cols="4"
             style="border-bottom: white 1px solid; aspect-ratio: 1/1; width: 33%">
        <v-btn width="100%" height="100%" style="font-size: 9vw;" @click="handleMove(1, 2)"><h1>{{
            game.board[0][1]
          }}</h1></v-btn>
      </v-col>
      <v-col cols="4"
             style="border-left: white 1px solid; border-bottom: white 1px solid; aspect-ratio: 1/1; width: 33%">
        <v-btn width="100%" height="100%" style="font-size: 9vw;" @click="handleMove(1, 3)"><h1>{{
            game.board[0][2]
          }}</h1></v-btn>
      </v-col>
    </v-row>
    <v-row no-gutters>
      <v-col cols="4"
             style="border-right: white 1px solid; aspect-ratio: 1/1; width: 33%">
        <v-btn width="100%" height="100%" style="font-size: 9vw;" @click="handleMove(2, 1)"><h1>{{
            game.board[1][0]
          }}</h1></v-btn>
      </v-col>
      <v-col cols="4"
             style="aspect-ratio: 1/1; width: 33%">
        <v-btn width="100%" height="100%" style="font-size: 9vw;" @click="handleMove(2, 2)"><h1>{{
            game.board[1][1]
          }}</h1></v-btn>
      </v-col>
      <v-col cols="4"
             style="border-left: white 1px solid; aspect-ratio: 1/1; width: 33%">
        <v-btn width="100%" height="100%" style="font-size: 9vw;" @click="handleMove(2, 3)"><h1>{{
            game.board[1][2]
          }}</h1></v-btn>
      </v-col>
    </v-row>
    <v-row no-gutters>
      <v-col cols="4"
             style="border-right: white 1px solid;border-top: white 1px solid;aspect-ratio: 1/1; width: 33%">
        <v-btn width="100%" height="100%" style="font-size: 9vw;" @click="handleMove(3, 1)"><h1>{{
            game.board[2][0]
          }}</h1></v-btn>
      </v-col>
      <v-col cols="4"
             style="border-top: white 1px solid; aspect-ratio: 1/1; width: 33%">
        <v-btn width="100%" height="100%" style="font-size: 9vw;" @click="handleMove(3, 2)"><h1>{{
            game.board[2][1]
          }}</h1></v-btn>
      </v-col>
      <v-col cols="4"
             style="border-left: white 1px solid; border-top: white 1px solid; aspect-ratio: 1/1; width: 33%">
        <v-btn width="100%" height="100%" style="font-size: 9vw;" @click="handleMove(3, 3)"><h1>{{
            game.board[2][2]
          }}</h1></v-btn>
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

import {onUnmounted, ref, watch} from 'vue';
import {useNameStorage} from "@/stores/nameStorage";
import gameService from "@/api/service/GameService";
import {useRoute, useRouter} from "vue-router";

const route = useRoute()
const router = useRouter()
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

    parseGame(data.body)
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
  game.gameState = parsedJson.gameState


  const newBoard = [
    ['', '', ''],
    ['', '', ''],
    ['', '', '']
  ]
  for (let i = 0; i < parsedJson.board.length; i++) {
    for (let j = 0; j < parsedJson.board[i].length; j++) {
      newBoard[i][j] = parsedJson.board[i][j] === null ? '' : parsedJson.board[i][j];
    }
  }

  game.board = newBoard
}

function sendJoinRequest() {
  socket.send(`/request/join/${route.params.id}`, JSON.stringify({
    playerToken: token.value,
    playerName: playerName.value
  }))
}

function handleMove(x, y) {

  x--
  y--

  if (!canPlay(x, y)) {
    console.error('Cannot play at this position')
    return
  }

  sendMoveRequest(x, y)
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

function canPlay(x = null, y = null) {
  if (x === null || y === null) {
    console.error('x or y is null')
    return game.currentPlayer === playerName.value
  }

  if (x < 0 || x >= game.board.length || y < 0 || y >= game.board[0].length) {
    console.error('Index out of bounds. x: ' + x + ', y: ' + y + ', board: ' + JSON.stringify(game.board))
    return false
  }

  console.log("Current player: " + game.currentPlayer)
  console.log("Player name: " + playerName.value)

  console.log("Board Value: " + game.board[x][y])

  console.log("Is current player: " + game.currentPlayer === playerName.value)
  console.log("Is empty: " + game.board[x][y] === '')
  return game.currentPlayer === playerName.value && game.board[x][y] === ''
}

function sendMoveRequest(x, y) {

  console.log("Sending move request:" + JSON.stringify({
    playerToken: token.value,
    x: x,
    y: y
  }));

  socket.send(`/request/move/${route.params.id}`, JSON.stringify({
    playerToken: token.value,
    x: x,
    y: y
  }))

}

function leave() {
  router.push('/')
}

function myTurn() {
  return game.currentPlayer === playerName.value
}

watch(game, (newGame) => {
  const newGameState = newGame.gameState
  switch (newGameState) {
    case 'WAITING': // esperando alguem entrar
      console.log('Waiting for someone to join')
      break
    case 'RUNNING': // jogo em andamento
      console.log('Game is running')
      break
    case 'DRAW': // empate
      console.log('Game is a draw')
      break
    case 'DISCONNECTED': // alguem saiu
      console.log('Someone disconnected')
      break
    case 'FINISHED': // alguem ganhou
      console.log('Game is finished')
      break
    default:
      console.error('Invalid game state: ' + newGameState)
  }

})

</script>

<style scoped lang="sass">

</style>
