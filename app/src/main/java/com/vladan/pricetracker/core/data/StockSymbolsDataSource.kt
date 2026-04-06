package com.vladan.pricetracker.core.data

import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class StockSymbolsDataSource @Inject constructor() {

    internal val all = listOf(
        StockInfo("AAPL", "Apple Inc.", 189.84, "Apple designs, manufactures, and markets smartphones, personal computers, tablets, wearables, and accessories worldwide."),
        StockInfo("MSFT", "Microsoft Corp.", 420.55, "Microsoft develops and supports software, services, devices, and solutions worldwide across productivity, cloud, and personal computing."),
        StockInfo("GOOGL", "Alphabet Inc.", 176.33, "Alphabet provides online advertising services through Google, as well as cloud computing, hardware products, and technology research."),
        StockInfo("AMZN", "Amazon.com Inc.", 186.51, "Amazon engages in e-commerce, cloud computing (AWS), digital streaming, and artificial intelligence services globally."),
        StockInfo("NVDA", "NVIDIA Corp.", 131.89, "NVIDIA designs GPU-accelerated computing platforms for gaming, professional visualization, data centers, and automotive markets."),
        StockInfo("META", "Meta Platforms Inc.", 544.32, "Meta builds technologies for connecting people through social networking apps including Facebook, Instagram, and WhatsApp."),
        StockInfo("TSLA", "Tesla Inc.", 248.42, "Tesla designs, manufactures, and sells electric vehicles, energy storage systems, and solar energy generation systems."),
        StockInfo("BRK.B", "Berkshire Hathaway", 457.30, "Berkshire Hathaway is a diversified holding company owning subsidiaries in insurance, freight rail, energy, manufacturing, and retail."),
        StockInfo("JPM", "JPMorgan Chase", 211.75, "JPMorgan Chase is a global financial services firm offering investment banking, asset management, and consumer banking."),
        StockInfo("V", "Visa Inc.", 289.10, "Visa operates a global payments technology network facilitating electronic funds transfers through branded credit and debit cards."),
        StockInfo("JNJ", "Johnson & Johnson", 156.42, "Johnson & Johnson researches, develops, manufactures, and sells healthcare products in pharmaceuticals, medical devices, and consumer health."),
        StockInfo("WMT", "Walmart Inc.", 172.89, "Walmart operates a chain of hypermarkets, discount stores, and grocery stores across multiple countries worldwide."),
        StockInfo("PG", "Procter & Gamble", 167.33, "Procter & Gamble manufactures and distributes branded consumer packaged goods including beauty, grooming, health, and home care products."),
        StockInfo("MA", "Mastercard Inc.", 473.60, "Mastercard operates a global payments processing network connecting consumers, financial institutions, merchants, and governments."),
        StockInfo("UNH", "UnitedHealth Group", 527.15, "UnitedHealth Group provides health care coverage and benefits services through UnitedHealthcare and Optum health services."),
        StockInfo("HD", "Home Depot Inc.", 378.22, "Home Depot operates home improvement retail stores selling building materials, home improvement products, and lawn and garden supplies."),
        StockInfo("DIS", "Walt Disney Co.", 112.45, "Disney operates entertainment businesses in theme parks, media networks, studio entertainment, and direct-to-consumer streaming."),
        StockInfo("NFLX", "Netflix Inc.", 698.50, "Netflix provides subscription streaming entertainment services offering TV series, documentaries, feature films, and mobile games worldwide."),
        StockInfo("ADBE", "Adobe Inc.", 487.92, "Adobe provides digital media and digital marketing solutions including Creative Cloud, Document Cloud, and Experience Cloud platforms."),
        StockInfo("CRM", "Salesforce Inc.", 272.38, "Salesforce provides cloud-based customer relationship management technology enabling companies to manage sales, service, and marketing."),
        StockInfo("INTC", "Intel Corp.", 31.22, "Intel designs and manufactures computing and networking components including processors, chipsets, and connectivity products."),
        StockInfo("AMD", "Advanced Micro Devices", 164.78, "AMD designs and produces microprocessors, GPUs, and adaptive computing solutions for data centers, gaming, and embedded markets."),
        StockInfo("PYPL", "PayPal Holdings", 78.55, "PayPal operates a digital payments platform enabling digital and mobile payments for consumers and merchants globally."),
        StockInfo("BA", "Boeing Co.", 188.92, "Boeing designs, manufactures, and sells commercial jetliners, defense products, space systems, and provides related services."),
        StockInfo("COST", "Costco Wholesale", 891.45, "Costco operates membership-based warehouse clubs offering a wide selection of merchandise including groceries, electronics, and apparel.")
    )
}

internal data class StockInfo(
    val symbol: String,
    val companyName: String,
    val basePrice: Double,
    val description: String
)
