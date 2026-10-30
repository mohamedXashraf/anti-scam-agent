package tech.mohamedashraf.domain.rules

/*

JSON Scheme:

{
  "notes": {
    "type": "List",
    "parameters": {
      "note": {
        "type": "String",
        "description": "Observation or verification check detail"
      },
      "accepted": {
        "type": "Boolean",
        "description": "Whether this verification check passed/is accepted"
      }
    }
  },
  "recommendation": {
    "type": "String",
    "description": "final recommendation prase whether Buy or Bargain or Scam and should be avoided"
  }
}

*/

data class DealVerification(
    val notes: List<DealVerificationNote> = listOf(),
    val recommendation: String = ""
)

data class DealVerificationNote(
    val note: String = "",
    val accepted: Boolean = false
)