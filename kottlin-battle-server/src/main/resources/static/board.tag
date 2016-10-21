<board>
    <table>
        <tr each={ new Array(dimension.y) } onclick={change}>
            <td each={ new Array(dimension.x) }>coucou</td>
        </tr>
    </table>

    this.dimension = opts.dimension;
    this.lines = new Array(opts.dimension.y);

    for(var index = 0; index < myArray.length; index++) {

    }

    change() {
        console.log(arguments);
    }
</board>
