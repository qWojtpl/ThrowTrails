<p align="center">
  <img src="https://media.discordapp.net/attachments/816647374239694849/1103646911614959698/a9de0793d204b7b5b87dc683c8d58584955bac35da39a3ee5e6b4b0d3255bfef95601890afd80709da39a3ee5e6b4b0d3255bfef95601890afd80709f654dc9057c3331c282f0c5447140c11.png">
</p>

<br>

# ThrowTrails

<p>Add trails to projectiles to your Minecraft server.</p>
<p>Tested minecraft versions: </p> 

`1.19.3`

# Installation

<p>Put ThrowTrails.jar to your plugins folder and restart the server.</p>

# Configuration

`%nl% - new line in lore`<br>

<details><summary>config.yml</summary>

## Messages

`title` - GUI title<br>
`reload` - Successful reload message<br>
`mustBePlayer` - Message when console want to open throwtrails menu<br>
`selected` - In lore, message when trail is selected by player<br>
`clickToSelect` - In lore, message when trail can be selected by player<br>
`nextPage` - Next page<br>
`previousPage` - Previous page<br>
`clearTrails` - Item name - when clicked resets player's trail preferences<br>
`clearTrailsDescription` - Clear trails item lore<br>
`noTrails` - Item with this name will appear if player doesn't have any trails<br>
`noTrailsDescription` - Item lore<br>
`setTrail` - Message when player will set trail. Use {0} for trail name<br>
`clearTrail` - Message when player will clear trail preferences<br>

## Trails

You can use colors in IDs.

`icon` - Material, trail icon in GUI<br>
`loop` - When set to true then lifecycle will be looped until projectile hit/death<br>
`permission` - Permission to use this trail<br>
`delay` - Delay (in ticks). After this delay lifecycle will start<br>
`interval` - (in ticks), time every next lifecycle<br>
`notApplies` - List of projectiles that this trail doesn't apply to<br>
`lifeCycle` - List of particles that will be spawned when projectile is flying. You can spawn multiple particles at once, just use "/" for next particle<br>

</details>

# Commands & Permissions

`/throwtrails` - Open throwtrails menu (it not requires any permission)<br>
`/slady` - Alias for /throwtrails<br>
`/throwtrails reload` - Reload configuration (permission: thorwtrails.reload)
