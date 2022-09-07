package machine

enum class Drink(
    val water: Int,
    val milk: Int,
    val beans: Int,
    val cost: Int,
    val cups: Int = 1
) {
    ESPRESSO(250, 0, 16, 4),
    LATTE(350, 75, 20, 7),
    CAPPUCCINO(200, 100, 12, 6),
}

class Machine(private var moneyStored: Int = 550,
              private var waterStored: Int = 400,
              private var milkStored: Int = 540,
              private var beansStored: Int = 120,
              private var cupsStored: Int = 9) {
    enum class STATE(val action: String) {
        BUYING("buy"),
        FILLING("fill"),
        TAKING("take"),
        TELLING("remaining");
    }

    fun validAction(passedAction: String): String{
        for (enum in STATE.values()) {
            if (enum.action == passedAction) return enum.action else continue
        }
        return ""
    }

    fun act(action: String) {
        when (action) {
            "buy" -> this.buy()
            "fill" -> this.fill()
            "take" -> this.take()
            "remaining" -> this.printState()
        }
    }


    private fun buy() {
        print("What do you want to buy? 1 - espresso, 2 - latte, 3 - cappuccino, back - to main menu: ")
        when (readln()) {
            "1" -> buyDrink(Drink.ESPRESSO)
            "2" -> buyDrink(Drink.LATTE)
            "3" -> buyDrink(Drink.CAPPUCCINO)
            "back" -> {
                println()
                return
            }
            else -> {
                println("Please select a valid option, or enter back to cancel")
                buy()
            }
        }
        println()
    }

    private fun buyDrink(drinkToBuy: Drink) {
        val resource = enoughIngredients(drinkToBuy)
        if (resource.isEmpty()) {
            println("I have enough resources, making you a coffee!")
            makeDrink(drinkToBuy)
        } else println("Sorry, not enough $resource!")
    }

    private fun enoughIngredients(drinkToMake: Drink): String {
        return when (false) {
            enoughItem(waterStored, drinkToMake.water) ->  "water"
            enoughItem(milkStored, drinkToMake.milk) -> "milk"
            enoughItem(beansStored, drinkToMake.beans) -> "beans"
            enoughItem(cupsStored, drinkToMake.cups) -> "cups"
            else -> ""
        }
    }

    private fun enoughItem(itemStocked: Int, ratio: Int): Boolean {
        return itemStocked >= ratio
    }

    private fun makeDrink(drinkToMake: Drink) {
        waterStored -= drinkToMake.water
        milkStored -= drinkToMake.milk
        beansStored -= drinkToMake.beans
        moneyStored += drinkToMake.cost
        cupsStored -= drinkToMake.cups
    }

    private fun fill() {
        print("Write how many ml of water do you want to add: ")
        waterStored += readln().toInt()
        print("Write how many ml of milk do you want to add: ")
        milkStored += readln().toInt()
        print("Write how many grams of coffee beans do you want to add: ")
        beansStored += readln().toInt()
        print("Write how many disposable cups of coffee do you want to add: ")
        cupsStored += readln().toInt()
        println()
    }

    private fun take() {
        println("I gave you $moneyStored\n")
        moneyStored = 0
    }

    private fun printState() {
        println("The coffee machine has: \n" +
                "$waterStored ml of water \n" +
                "$milkStored ml of milk \n" +
                "$beansStored g of coffee beans \n" +
                "$cupsStored disposable cups \n" +
                "$$moneyStored of money\n")
    }

}

fun main() {
    val weOnlyHaveOneCoffeeMachine = Machine()
    prompt(weOnlyHaveOneCoffeeMachine)
}

fun prompt(passedMachine: Machine) {
    print("Write action (buy, fill, take, remaining, exit): ")
    when (val userInput = readln()) {
        passedMachine.validAction(userInput) -> passedMachine.act(userInput)
        "exit" -> return
        else -> {
            println("Sorry, please select a valid option:")
            prompt(passedMachine)
        }
    }
    prompt(passedMachine)
}


