(ns clj-github-issues.test-data)

(def test-data-open
  [{:labels [],
  :pull_request {:html_url nil, :diff_url nil, :patch_url nil},
  :labels_url
  "https://api.github.com/repos/technomancy/leiningen/issues/1327/labels{/name}",
  :state "open",
  :updated_at "2013-09-14T04:58:59Z",
  :closed_at nil,
  :html_url "https://github.com/technomancy/leiningen/issues/1327",
  :title
  "`lein upgrade` should explicitly tell me when it's already up-to-date",
  :created_at "2013-09-14T04:58:59Z",
  :url
  "https://api.github.com/repos/technomancy/leiningen/issues/1327",
  :comments 0,
  :events_url
  "https://api.github.com/repos/technomancy/leiningen/issues/1327/events",
  :number 1327,
  :assignee nil,
  :id 19491180,
  :milestone nil,
  :comments_url
  "https://api.github
.com/repos/technomancy/leiningen/issues/1327/comments"}
 {:labels [],
  :labels_url
  "https://api.github.com/repos/technomancy/leiningen/issues/1324/labels{/name}",
  :state "open",
  :updated_at "2013-09-12T02:52:32Z",
  :closed_at nil,
  :html_url "https://github.com/technomancy/leiningen/pull/1324",
  :title "Added support of maven <plugin> in pom.xml (pom-plugin tag)",
  :created_at "2013-09-12T00:22:20Z",
  :url
  "https://api.github.com/repos/technomancy/leiningen/issues/1324",
  :comments 0,
  :events_url
  "https://api.github.com/repos/technomancy/leiningen/issues/1324/events",
  :number 1324,
  :assignee nil,
  :id 19355365,
  :milestone nil,
  :comments_url
  "https://api.github.com/repos/technomancy/leiningen/issues/1324/comments"}])

(def test-data-closed
  [{:labels [],
  :pull_request {:html_url nil, :diff_url nil, :patch_url nil},
  :labels_url
  "https://api.github.com/repos/technomancy/leiningen/issues/1326/labels{/name}",
  :state "closed",
  :updated_at "2013-09-12T19:07:58Z",
  :closed_at "2013-09-12T19:07:58Z",
  :html_url "https://github.com/technomancy/leiningen/issues/1326",
  :title "`lein test` mishandling macros inside of is forms",
  :created_at "2013-09-12T17:29:47Z",
  :url
  "https://api.github.com/repos/technomancy/leiningen/issues/1326",
  :comments 4,
  :events_url
  "https://api.github.com/repos/technomancy/leiningen/issues/1326/events",
  :number 1326,
  :assignee nil,
  :id 19401899,
  :milestone nil,
  :comments_url
  "https://api.github.com/repos/technomancy/leiningen/issues/1326/comments"}
 {:labels [],
  :pull_request {:html_url nil, :diff_url nil, :patch_url nil},
  :labels_url
  "https://api.github.com/repos/technomancy/leiningen/issues/1325/labels{/name}",
  :state "closed",
  :updated_at "2013-09-12T01:06:09Z",
  :closed_at "2013-09-12T01:06:09Z",
  :html_url "https://github.com/technomancy/leiningen/issues/1325",
  :title "Can't seem to avoid AOT warning",
  :created_at "2013-09-12T00:56:37Z",
  :url
  "https://api.github.com/repos/technomancy/leiningen/issues/1325",
  :comments 1,
  :events_url
  "https://api.github.com/repos/technomancy/leiningen/issues/1325/events",
  :number 1325,
  :assignee nil,
  :id 19356317,
  :milestone nil,
  :comments_url
  "https://api.github.com/repos/technomancy/leiningen/issues/1325/comments"}])
