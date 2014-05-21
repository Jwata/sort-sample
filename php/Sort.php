<?php
class Sort
{
    public static function execBubbleSort(array $numbers)
    {
        $array_size = count($numbers);

        for ($i = 0; $i < ($array_size - 1); $i++) {
            for ($j = $array_size - 1; $j > $i; $j--) {
                if ($numbers[$j - 1] > $numbers[$j]) {
                    $tmp = $numbers[$j];

                    $numbers[$j] = $numbers[$j - 1];
                    $numbers[$j - 1] = $tmp;
                }
            }
        }

        print_r($numbers);
    }

    public static function execQuickSort(array $numbers)
    {
        $array_size = count($numbers);
        if ($array_size <= 1) {
            return $numbers;
        }

        $pivot = $numbers[0];

        $left  = [];
        $right = [];
        for ($i = 1; $i < $array_size; $i++) {
            if ($numbers[$i] <= $pivot) {
                $left[] = $numbers[$i];
            } else {
                $right[] = $numbers[$i];
            }
        }

        unset ($numbers);

        $left  = self::execQuickSort($left);
        $right = self::execQuickSort($right);

        return array_merge($left, [$pivot], $right);
    }

    public static function execHeapSort(array $numbers)
    {
        $array_size = count($numbers);

        // build heap
        for ($i = ($array_size / 2) - 1; $i >= 0; $i--) {
            $numbers = self::siftDown($numbers, $i, $array_size - 1);
        }

        print_r($numbers);

        // exec heap sort
        $temp = null;

        for ($i = $array_size - 1; $i >= 1; $i--) {
            $temp = $numbers[0];
            $numbers[0] = $numbers[$i];
            $numbers[$i] = $temp;
            $numbers = self::siftDown($numbers, 0, $i - 1);
        }

        print_r($numbers);
    }

    private static function siftDown(array $numbers, $root, $bottom)
    {
        $is_done = false;
        $max_child = null;
        while ($root * 2 <= $bottom && !$is_done) {
            if ($root * 2 == $bottom) {
                $max_child = $root * 2;
            }
            else if ($numbers[$root * 2] > $numbers[$root * 2 + 1]) {
                $max_child = $root * 2;
            }
            else {
                $max_child = $root * 2 + 1;
            }

            if ($numbers[$root] < $numbers[$max_child]) {
                $temp = $numbers[$root];
                $numbers[$root] = $numbers[$max_child];
                $numbers[$max_child] = $temp;
                $root = $max_child;
            } else {
                $is_done = true;
            }
        }

        return $numbers;
    }

    public static function execCountSort(array $numbers)
    {
        $max_value = max($numbers);
        $array_size = count($numbers);

        $count = [];

        for ($i = 0; $i < $array_size; $i++) {
            if (isset($count[$numbers[$i]])) {
                $count[$numbers[$i]]++;
            } else {
                $count[$numbers[$i]] = 0;
            }
        }

        $temp = 0;
        foreach ($count as &$c) {
            $c += $temp;
            $temp = $c;
        }

        $sorted_numbers = [];

        for ($i = $array_size - 1; $i >= 0; $i--) {
            $count[$i] = $numbers[$i];
            $sorted_numbers[(int)$count[$i]] = $numbers[$i];

            $count[$i]--;
        }

        print_r($sorted_numbers);
    }
}

$sample_num = 10000;
$sample_numbers = [];

$i = 0;
while ($i < $sample_num) {
    $sample_numbers[] = mt_rand();
    $i++;
}

// before exec
print "before\n";
print_r($sample_numbers);

$start = microtime();

Sort::execCountSort($sample_numbers);
//Sort::execHeapSort($sample_numbers);
//$sorted_numbers = Sort::execQuickSort($sample_numbers);
//print_r($sorted_numbers);
//Sort::execBubbleSort($sample_numbers);
//sort($sample_numbers);
//print_r($sample_numbers);

$end = microtime();
$elapsed = $end - $start;
print "elapsed time {$elapsed}.";
