:require postgresql-42.2.6.jar
import java.util.Properties
val url = "jdbc:postgresql://localhost:5432/TBC?user=postgres&password=123456"
val connectionProperties = new Properties()
connectionProperties.setProperty("Driver", "org.postgresql.Driver")
val currency = "(SELECT * FROM currency ) as currency "
val transaction= "(SELECT * FROM transaction) as transaction"
val client= "(SELECT * FROM client) as client"
val currencydf = spark.read.jdbc(url, currency , connectionProperties)
val transactiondf = spark.read.jdbc(url, transaction , connectionProperties)
val clientdf = spark.read.jdbc(url, client, connectionProperties)
val joined_df = transactiondf.join(currencydf ,transactiondf("currencyid") ===  currencydf ("id"),"left")
val final_joined=joined_df.join(clientdf, joined_df("iban") === clientdf("iban") && joined_df("date") === clientdf("date"),"inner" ).drop(clientdf("iban")).drop(clientdf("date"))
val trans_num=transactiondf.groupBy("iban").count()
val trans_avg_amount=transactiondf.groupBy("iban").avg("amount")
val final_data=final_joined.join(trans_num ,final_joined("iban") ===  trans_num ("iban"),"leftouter").drop(trans_num("iban"))
val final_version=final_data.join(trans_avg_amount,final_data("iban") === trans_avg_amount("iban"),"leftouter").drop(trans_avg_amount("iban"))
final_version.write
  .option("header", "true")
  .option("encoding", "UTF-8")
  .csv("file:///C:/out.csv")
