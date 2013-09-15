# github-issue-charts

A Clojure webapp for displaying charts of github issues over time.

## Usage

    git clone git@github.com:rkday/github-issue-charts.git
    cd github-issue-charts
    GITHUB_TOKEN=<token> lein ring server

Then browse to `http://localhost:3000/<user>/<repo>/graph` to see a chart of new, closed and total issues over time for that repository.

You can also use `/<user>/graph`, which shows the sum of all issues for that user's repositories, or `/<user>/<repo>/csv` or `/<user>/csv` for the data in CSV format.

## License

Copyright © 2013 Robert Day

Distributed under the Eclipse Public License either version 1.0 or (at
your option) any later version.
