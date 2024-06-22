<template>
  <v-sheet width="100%" height="100%">
    <v-container class="justify-center">
      <v-row class="justify-space-between align-center">
        <v-spacer></v-spacer>
        <v-col cols="auto" align-self="end">
          <v-btn @click="toggleTheme" variant="flat" :theme="altTheme.contrastColor">
            <v-icon class="mdi mdi-theme-light-dark"></v-icon>
          </v-btn>
        </v-col>
      </v-row>
    </v-container>
    <v-container class="justify-center flex flex-column align-center fill-height pa-0">
      <v-card class="justify-center align-center pa-6" style="aspect-ratio: 2/3; max-width: 60vh" rounded="lg">
        <v-card-title class="text-center mb-5">
          <h4> JUBILADOS JUBILAM JUNTOS</h4>
        </v-card-title>
        <v-container class="fill-height flex-column pa-0 justify-center align-center" style="width: 100%">
          <v-container class="fill-height flex-column">
            <v-sheet class="pa-6" width="100%" elevation="5" rounded="lg">
              <v-row justify="center">
                <v-col cols="12" class="text-center">
                  <p class="text-body-1">
                    ESCOLHA SEU NOME NO JOGO:
                  </p>
                  <v-spacer></v-spacer>
                  <v-text-field label="nome de usuário" class="text-body-2" variant="underlined"></v-text-field>
                </v-col>
              </v-row>
            </v-sheet>
            <v-spacer></v-spacer>
            <v-row class="pt-6 pb-3 justify-center" no-gutters style="width: 100%">
              <v-col style="height: 100%">
                <v-btn size="x-large"
                       rounded="lg"
                       variant="flat"
                       elevation="5"
                       width="100%"
                       height="100%"
                       @click="createNewGame">
                  <p class="text-body-1">
                    CRIAR PARTIDA
                  </p>
                </v-btn>
              </v-col>
            </v-row>
            <v-row class="justify-center pt-6 pb-3" no-gutters style="width: 100%">
              <v-col cols="12">
                <v-btn size="x-large"
                       rounded="lg"
                       variant="flat"
                       elevation="5"
                       width="100%"
                       height="100%"
                       @click="createNewGame">
                  <p class="text-body-1">
                    ENTRAR EM PARTIDA ALEATÓRIA
                  </p>
                </v-btn>
              </v-col>
            </v-row>
            <v-row class="justify-center pt-6 pb-3" no-gutters style="width: 100%">
              <v-col>
                <v-dialog max-width="500"
                          transition="dialog-bottom-transition"
                          :persistent="otpDialog.persistence">
                  <template v-slot:activator="{ props: activatorProps }">
                    <v-btn size="x-large"
                           rounded="lg"
                           variant="flat"
                           elevation="5"
                           width="100%"
                           height="100%"
                           v-bind="activatorProps">
                      <p class="text-body-1">
                        ENTRAR EM PARTIDA PRIVADA
                      </p>
                    </v-btn>
                  </template>
                  <template v-slot:default="{ isActive }">
                    <v-card
                      rounded="lg"
                      elevation="5">
                      <v-card-text>
                        <v-row>
                          <v-col class="justify-center align-center text-center">
                            <p class=" text-body-1"> INSIRA A SENHA DA PARTIDA</p>
                            <v-otp-input :loading="otpDialog.loading"
                                         v-model="otpDialog.code"
                                         type="text"
                                         style="aspect-ratio: 10/1"
                                         variant="underlined"
                                         v-on:finish="checkOTPCode">
                            </v-otp-input>
                          </v-col>
                        </v-row>
                      </v-card-text>

                      <v-card-actions class="justify-center pa-6">
                        <v-btn
                          color="red"
                          size="x-large"
                          rounded="lg"
                          variant="flat"
                          elevation="5"
                          width="100%"
                          @click="isActive.value = false; otpDialog.loading=null"
                        >
                          <div class="text-body-1
                        cursor-pointer">
                            CANCELAR
                          </div>
                        </v-btn>
                      </v-card-actions>
                    </v-card>
                  </template>
                </v-dialog>
              </v-col>
            </v-row>
            <v-spacer></v-spacer>
          </v-container>
        </v-container>
      </v-card>
    </v-container>
  </v-sheet>
</template>

<style>
</style>

<script setup>
import gameService from "@/api/service/GameService";
import {useRouter} from "vue-router";
import {reactive} from "vue";
import {useTheme} from "vuetify";

const router = useRouter()

const otpDialog = reactive({
  loading: false,
  code: '',
  persistence: false
})

const theme = useTheme()

// eslint-disable-next-line no-undef
const altTheme = reactive({
  contrastColor: 'white'
})

function checkOTPCode() {
  otpDialog.loading = "loading";

  console.log("Otp: " + otpDialog.code.toUpperCase())

  joinGameByOtp(otpDialog.code.toUpperCase())

  otpDialog.persistence = true;
}

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
      router.push("/game/" + response.data.id)
    })
    .catch(error => {
      console.log(error)
    })
}

function toggleTheme() {
  if (theme.global.current.value.dark) {
    theme.global.name.value = 'light';
    altTheme.contrastColor = "dark"
  } else {
    theme.global.name.value = 'dark';
    altTheme.contrastColor = "light"
  }
}


</script>

<style scoped lang="scss">


</style>
