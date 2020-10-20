## Game Workthrough
# Gaming panel
When starting the program, the first panel you will see is the entering the number of players for the game. The specification shows that only 2 to 5 people can play the game, so when you enter a character that is not from 2 to 5, clicking staring the carcassonne button will do nothing.

After entering the game, there will be a scrollable panel (I did not make it resizable since I have tried and found out it is weird) that consist of a lot of 2D array buttons and a information panel that locate on the right hand side of the game board

From up to down: Each player ID with the number of meeples the player currently has and the points the player current has.

Then it is the drop down menu that has selections for player to select the place to put down a meeple: "-" for not puting a meeple, center, left, right, up, and down. (The positions are relative to the tile instead of the image)

Next is the image on the tile stack top.

Then it is a confirm button that confirms the place the player wants to place a meeple. If success, the meeple will show on the board and the player will have 1 meeple less on the player information part.

Next is the rotate button that allows players to rotate the tile on the top of the tile stack.

Finally, it is an abandon button that allows player to skip current tile if there is no place to put down the tile.

# Game rule
After start the game, at first there is no center tile on the board. Don't panic! Because the center board is down at the middle of the whole 2D array buttons. Just scroll vertically and horizontally to the middle of the whole 2D arrays and you will find it :)

One player will first rotate a tile or put down a tile on the board. If the placement is invalid, then the tile image will not shown on the board.

After putting down a tile, the next tile will on the tile stack top will show on the right hand information panel. The dropdown menu and the confirm button will then be enabled for player to select the position for placing a meeple. If the position for placing a meeple is valid, then the meeple will show on the image. If not, there is nothing showing on the tile. But currently the meeple will not reduced since the player has not click on the confirmed button. (A player has to select one position even he or she found that there is no place to put down a meeple, or the player will messed up the game. Say last player select left in last turn, current player found there is no place to put down a meeple, then current player has to reselect left again just in case not messing with the game logic. Additionally, for the tile type J and K, if the player choose center to put down the meeple, the dot will be a little bit off the center but it is actually representing the meeple on the road in the center.)

Then the player will have to click the confirm button to really place a meeple on the tile if there is a valid position for the player to place one. Then the game will start scoring from this tile, including returning meeples and adding score to the corresponding player.

Important: do not place a tile before clicking confirm button, or you will mess up the game

After the last tile is placed on the board and the user clicked the confirm button, the game will first score the last placed tile, then score the whole board for those tile that has not been the scored to the players. And then the game will get the player with the highest score and prompt a winner message showing the winner player with the score and the meeples left.

# Other
The change for the design documents will stated in the discussion.pdf.