# Welcome to Wallet!
### Wallet is a project designed to connect Discord's economy bots. With a simple installation and set-up, any dev can add their bot to the Wallet economy!

> Questions? Comments? Concerns? Join the Discord server!<br/>
> **https://discord.gg/aKGv9z2g8B**

<br/>

## Getting Started
```java
Wallet.addToWallet(client, userID, amountOfCurrency, currencySymbol /* Optional, defaults to $ */);

Wallet.removeFromWallet(client, userID, amountOfCurrency, currencySymbol);

Wallet.checkBalance(client, userID, currencySymbol);

// Including a currency symbol will affect how your currency is viewed in the Wallet bot.
```
<br/>

Warning: Abuse of any of Wallet's features will have your bot banned. Think you've found an exploit? Let us know in the Discord server.
