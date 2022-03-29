# ROI dapps calculator

### Features

- Scrap all invest transaction from ROI app contract
- Show how much was invested into each plan
- Estimate contract lifespan

### Supported chains
- AVALANCHE
- POLYGON
- BINANCE SMART CHAIN
- FANOTM


### Requirements:

- Java 1.8+
- Chrome browser

### How to run:

1. Download the project
2. Open project folder and edit `config.json` file in the root folder
3. Double click `run.bat`
4. Wait for results

_I do not know MACOS sorry :(_

### How it works:

1. Runs Chrome browser in headless mode via Selenium library
2. Go to Polyscan/Bscscan/FTMscan/Snowtrace
3. Grabs all _Invest_ transactions IDs from contract page
4. Scrap all transactions data - timestamp, value, success, plan
5. Calculate predicted contract health span

### Config.json
In order to scan for your ROI app fill selected info:

- chain (POLYGON/BINANCE/FANTOM/AVALANCHE)
- contract address (can be found on ROI app or HazeCrypto audit page)
- devFee (can be found in HazeCrypto audit. Ex. if 10% use 10)
- planData (can be found in HazeCryptoAudit. Keep order as in audit)

### Warning

These are only rough estimations of ROI app lifespan to give you probable time when contract depletes. The app does not guarantee correctness in any way.

* Calculations are based on existing transactions, plans, developer and withdrawal fees
* Calculations do not take any advanced contract sustain methods into considerations nor dynamic ROIs
* Calculations base on assumption that 'Withdraw-any-time' plans are consumed at the end of their lifespan
* Calculation is based on the assumption that no further deposits will happen into the contract
* Negative values at the end mean that it would take this amount of currency to be deposited into the contract in order to last to the timestamp

### Support
You can find me on discord: `zac jungle#1011`

If you like the app you may consider donating ETH/MATIC/SHIB/SAITAMA/AVAX/BNB to 0x3b858B53F000e0DC7Bb3F87090911DE8d9b660B0
