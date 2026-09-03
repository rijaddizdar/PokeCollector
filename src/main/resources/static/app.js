const cardsContainer = document.getElementById('cards')
const resultsContainer = document.getElementById('results')
const form = document.getElementById('form')

function renderCards(cards, container) {
    container.textContent = ''
    for (const card of cards) {
        const el = document.createElement('p')
        el.textContent = card.name
        container.appendChild(el)

        if (card.image){
        const img=document.createElement('img')
        img.src=card.image+'/low.webp'
        img.width=150
        container.appendChild(img)

        }
    }
}

fetch('/api/cards')
    .then(response => response.json())
    .then(data => renderCards(data.content, cardsContainer))

form.addEventListener('submit', (e) => {
    e.preventDefault()

    const name = document.getElementById('name').value
    const number = document.getElementById('number').value
    const params = new URLSearchParams({name: name, number: number})


    fetch('/api/cards/search?' + params)
        .then(response => response.json())
        .then(cards => renderCards(cards, resultsContainer))

})
