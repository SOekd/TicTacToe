<template>
  <v-sheet width="100%" height="100%" class="pa-0">
    <v-container class="justify-center flex flex-column align-center fill-height pa-0">
      <v-container class="justify-center">
        <v-row class="justify-space-between align-center">
          <v-col cols="auto" align-self="center">
            <v-btn @click="leave" variant="flat" color="red">Sair</v-btn>
          </v-col>
          <v-col cols="auto" align-self="end">
            <v-btn @click="toggleTheme" variant="flat" :color="altTheme.contrastColor">
              <v-icon class="mdi mdi-theme-light-dark"></v-icon>
            </v-btn>
          </v-col>
        </v-row>
      </v-container>
      <v-container>
        <v-card style="width: 100%" :color="myTurn() ? 'null' : 'red'">
          <v-card-title class="text-center">
            <v-row>
              <v-col>
                <h4>ADVERSÁRIO</h4>
              </v-col>
            </v-row>
          </v-card-title>
          <v-row class="pa-4">
            <v-col cols="auto" align-self="center" style="text-align: center; width: 100%">
              <p>{{ getOpponentName() == null ? 'Aguardando o oponente!' : getOpponentName() }}</p>
            </v-col>
          </v-row>
        </v-card>
      </v-container>

      <v-spacer></v-spacer>

      <v-container class="flex flex-column" style="aspect-ratio: 1/1; max-width: 50vh">
        <v-row no-gutters>
          <v-col cols="4"
                 :style="{borderColor: altTheme.contrastColor}"
                 style="border-right: 3px solid; border-bottom: 3px solid; aspect-ratio: 1/1; width: 33%">
            <v-btn width="100%" height="100%" style="font-size: 5cqh;" @click="handleMove(1, 1)"><h1>{{
                game.board[0][0]
              }}</h1></v-btn>
          </v-col>
          <v-col cols="4"
                 :style="{borderColor: altTheme.contrastColor}"
                 style="border-bottom: 3px solid; aspect-ratio: 1/1; width: 33%">
            <v-btn width="100%" height="100%" style="font-size: 5cqh;" @click="handleMove(1, 2)"><h1>{{
                game.board[0][1]
              }}</h1></v-btn>
          </v-col>
          <v-col cols="4"
                 :style="{borderColor: altTheme.contrastColor}"
                 style="border-left: 3px solid; border-bottom: 3px solid; aspect-ratio: 1/1; width: 33%">
            <v-btn width="100%" height="100%" style="font-size: 5cqh;" @click="handleMove(1, 3)"><h1>{{
                game.board[0][2]
              }}</h1></v-btn>
          </v-col>
        </v-row>
        <v-row no-gutters>
          <v-col cols="4"
                 :style="{borderColor: altTheme.contrastColor}"
                 style="border-right: 3px solid; aspect-ratio: 1/1; width: 33%">
            <v-btn width="100%" height="100%" style="font-size: 5cqh;" @click="handleMove(2, 1)"><h1>{{
                game.board[1][0]
              }}</h1></v-btn>
          </v-col>
          <v-col cols="4"
                 style="aspect-ratio: 1/1; width: 33%">
            <v-btn width="100%" height="100%" style="font-size: 5cqh;" @click="handleMove(2, 2)"><h1>{{
                game.board[1][1]
              }}</h1></v-btn>
          </v-col>
          <v-col cols="4"
                 :style="{borderColor: altTheme.contrastColor}"
                 style="border-left:3px solid; aspect-ratio: 1/1; width: 33%">
            <v-btn width="100%" height="100%" style="font-size: 5cqh;" @click="handleMove(2, 3)"><h1>{{
                game.board[1][2]
              }}</h1></v-btn>
          </v-col>
        </v-row>
        <v-row no-gutters>
          <v-col cols="4"
                 :style="{borderColor: altTheme.contrastColor}"
                 style="border-right: 3px solid;border-top: 3px solid;aspect-ratio: 1/1; width: 33%">
            <v-btn width="100%" height="100%" style="font-size: 5cqh;" @click="handleMove(3, 1)"><h1>{{
                game.board[2][0]
              }}</h1></v-btn>
          </v-col>
          <v-col cols="4"
                 :style="{borderColor: altTheme.contrastColor}"
                 style="border-top: 3px solid; aspect-ratio: 1/1; width: 33%">
            <v-btn width="100%" height="100%" style="font-size: 5cqh;" @click="handleMove(3, 2)"><h1>{{
                game.board[2][1]
              }}</h1></v-btn>
          </v-col>
          <v-col cols="4"
                 :style="{borderColor: altTheme.contrastColor}"
                 style="border-left: 3px solid; border-top: 3px solid; aspect-ratio: 1/1; width: 33%">
            <v-btn width="100%" height="100%" style="font-size: 5cqh;" @click="handleMove(3, 3)"><h1>{{
                game.board[2][2]
              }}</h1></v-btn>
          </v-col>
        </v-row>

      </v-container>

      <v-spacer></v-spacer>

      <v-container>
        <v-card :color="myTurn() ? 'green' : 'null'">
          <v-card-title class="text-center">
            <h4>VOCÊ</h4>
          </v-card-title>
          <v-row class="pa-4">
            <v-col cols="auto" style="text-align: center; width: 100%">
              <p>{{ playerName }}</p>
            </v-col>
          </v-row>
        </v-card>
      </v-container>
      <v-dialog
        v-model="dialog.visible"
        transition="dialog-bottom-transition"
        :persistent="true">
        <v-card
          rounded="lg"
          elevation="5">
          <v-card-text>
            <v-row>
              <v-col class="justify-center align-center text-center">
                <h2>{{ dialog.content }}</h2>
                <v-label class="text-h5" v-show="dialog.showPassword">Senha: {{ game.otp }}</v-label>
              </v-col>
            </v-row>
          </v-card-text>

          <v-card-actions class="justify-center pa-6">
            <v-btn
              size="x-large"
              rounded="lg"
              variant="flat"
              elevation="5"
              width="100%"
              color="red"
              @click="leave"
            >
              <div class="text-body-1
                        cursor-pointer">
                SAIR
              </div>
            </v-btn>
          </v-card-actions>
        </v-card>
      </v-dialog>
    </v-container>
  </v-sheet>
</template>


<script setup>
import Stomp from 'webstomp-client'

import {onUnmounted, reactive, ref, watch} from 'vue';
import {useNameStorage} from "@/stores/nameStorage";
import gameService from "@/api/service/GameService";
import {useRoute, useRouter} from "vue-router";
import {useTheme} from "vuetify";

const route = useRoute()
const router = useRouter()
const joined = ref(false)

const nameStorage = useNameStorage()

// eslint-disable-next-line no-undef
const dialog = reactive({
  visible: false,
  content: '',
  showPassword: true
})

const theme = useTheme()

// eslint-disable-next-line no-undef
const altTheme = reactive({
  contrastColor: 'white'
})

// eslint-disable-next-line no-undef
const game = reactive({
  id: '',
  created: null,
  otp: '',
  publicGame: false,
  playerOne: null,
  playerTwo: null,
  winner: null,
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
  game.winner = parsedJson.winner
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

function toggleTheme() {
  if (theme.global.current.value.dark) {
    theme.global.name.value = 'light';
    altTheme.contrastColor = "black"
  } else {
    theme.global.name.value = 'dark';
    altTheme.contrastColor = "white"
  }
}

watch(game, (newGame) => {
  const newGameState = newGame.gameState
  switch (newGameState) {
    case 'WAITING': // esperando alguem entrar
      console.log('Waiting for someone to join')
      dialog.visible = true;
      dialog.showPassword = true;
      dialog.content = "Esperando por um adversário"

      break
    case 'RUNNING': // jogo em andamento
      console.log('Game is running')
      dialog.visible = false;
      break
    case 'DRAW': // empate
      console.log('Game is a draw')
      break
    case 'DISCONNECTED': // alguem saiu
      console.log('Someone disconnected')
      dialog.visible = true;
      dialog.showPassword = false;
      dialog.content = "O adversário saiu da partida"
      break
    case 'FINISHED': // alguem ganhou
      console.log('Game is finished')
      dialog.visible = true;
      dialog.showPassword = false;
      dialog.content = newGame.winner + "Venceu"
      break
    default:
      console.error('Invalid game state: ' + newGameState)
  }

})

</script>

<style scoped lang="sass">

</style>
