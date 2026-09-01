fetch('/api/cards')
.then(response=> response.json())
.then(data => {for(const card of data.content){
               const el=document.createElement("p")
               el.textContent=card.name
               document.body.appendChild(el)};
})
const container=document.getElementById('cards')



