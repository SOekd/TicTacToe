class NameService {

  getRandomName() {
    const names = ['Pankaja', 'Antigone', 'Maximón', 'Durga', 'Kartikeya']

    return names[Math.floor(Math.random() * names.length)] + Math.floor(Math.random() * 100)
  }

}

export default new NameService()
