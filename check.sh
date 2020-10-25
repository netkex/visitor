#! /bin/bash

./gradlew -q jar
pth_tst=""
pth_ans=""
num_of_tests=11
for ((tst = 1; tst <= num_of_tests; tst++))
do
    if (("$tst" < 10))
    then
      pth_tst="data/integTests/0$tst.in"
      pth_ans="data/integTests/0$tst.out"
    else
      pth_tst="data/integTests/$tst.in"
      pth_ans="data/integTests/$tst.out"
    fi

    java -jar build/libs/visitor.jar $pth_tst > cur.txt
    diff "cur.txt" "$pth_ans" > diff.txt
    if [ -s diff.txt ]
      then
        echo "$tst test: FAIL"
        exit 202
      else
        echo "$tst test: OK"
    fi
done


num_of_inc_tests=3
for ((tst = 1; tst <= num_of_inc_tests; tst++))
do
    if (("$tst" < 10))
    then
      pth_tst="data/integTests/inc0$tst.in"
      pth_ans="data/integTests/inc0$tst.out"
    else
      pth_tst="data/integTests/inc$tst.in"
      pth_ans="data/integTests/inc$tst.out"
    fi

    java -jar build/libs/visitor.jar $pth_tst > cur.txt
    diff "cur.txt" "$pth_ans" > diff.txt
    if [ -s diff.txt ]
      then
        echo "inc $tst test: FAIL"
        exit 202
      else
        echo "inc $tst test: OK"
    fi
done

rm -f diff.txt cur.txt
gradle clean

echo "tests passed"

exit 0