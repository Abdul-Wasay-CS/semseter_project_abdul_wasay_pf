public static boolean checkingDigits(String userPINsString)
{
    for(int i=0; i<userPINsString.length(); i++)
    {
        currentChar = userPINsString.charAt(i);

        if(!(Character.isDigit(currentChar)))
            return false;
    }

    return true;    //  only executes if all the characters were digits
}